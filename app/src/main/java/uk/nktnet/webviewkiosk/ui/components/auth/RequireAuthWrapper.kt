package uk.nktnet.webviewkiosk.ui.components.auth

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavController
import uk.nktnet.webviewkiosk.R
import uk.nktnet.webviewkiosk.managers.AuthenticationManager
import uk.nktnet.webviewkiosk.ui.components.common.LoadingIndicator
import uk.nktnet.webviewkiosk.utils.navigateToWebViewScreen

private fun showAuthPrompt(context: Context) {
    AuthenticationManager.showAuthenticationPrompt(
        title = context.getString(R.string.runtime_auth_required_title),
        description = context.getString(R.string.runtime_auth_required_description)
    )
}

@Composable
fun RequireAuthWrapper(
    navController: NavController,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    Box(modifier = Modifier.fillMaxSize()) {
        RequireAuthentication(
            onAuthenticated = content,
            onFailed = { errorResult ->
                AuthenticationErrorDisplay(
                    errorResult = errorResult,
                    onRetry = {
                        showAuthPrompt(context)
                    },
                    onCancel = {
                        navigateToWebViewScreen(navController)
                    },
                )
            }
        )
    }
}

@Composable
private fun RequireAuthentication(
    onAuthenticated: @Composable () -> Unit,
    onFailed: @Composable (AuthenticationManager.AuthenticationResult?) -> Unit
) {
    val context = LocalContext.current
    val authenticationResult by AuthenticationManager.promptResults.collectAsState(
        initial = AuthenticationManager.AuthenticationResult.Loading
    )

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                if (
                    authenticationResult != AuthenticationManager.AuthenticationResult.Pending
                    && !AuthenticationManager.checkAuthAndRefreshSession()
                ) {
                    showAuthPrompt(context)
                }
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    when (authenticationResult) {
        is AuthenticationManager.AuthenticationResult.Loading,
        is AuthenticationManager.AuthenticationResult.Pending -> {
            LoadingIndicator(stringResource(R.string.runtime_waiting_for_authentication))
        }
        is AuthenticationManager.AuthenticationResult.AuthenticationSuccess,
        is AuthenticationManager.AuthenticationResult.AuthenticationNotSet -> {
            onAuthenticated()
        }
        else -> onFailed(authenticationResult)
    }
}
