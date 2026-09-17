package uk.nktnet.webviewkiosk.ui.components.setting.fielditems.device

import android.Manifest
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import uk.nktnet.webviewkiosk.R
import uk.nktnet.webviewkiosk.config.UserSettings
import uk.nktnet.webviewkiosk.config.UserSettingsKeys
import uk.nktnet.webviewkiosk.ui.components.setting.fields.BooleanSettingFieldItem
import uk.nktnet.webviewkiosk.utils.rememberPermissionState

@Composable
fun AllowMicrophoneSetting() {
    val context = LocalContext.current
    val userSettings = UserSettings(context)
    val settingKey = UserSettingsKeys.Device.ALLOW_MICROPHONE

    val (
        permissionState,
        requestPermission
    ) = rememberPermissionState(Manifest.permission.RECORD_AUDIO)

    BooleanSettingFieldItem(
        label = stringResource(R.string.device_allow_microphone_title),
        infoText = stringResource(R.string.device_allow_microphone_info),
        initialValue = userSettings.allowMicrophone,
        settingKey = settingKey,
        restricted = userSettings.isRestricted(settingKey),
        onSave = { userSettings.allowMicrophone = it },
        itemText = { v ->
            val statusText = if (permissionState.granted) {
                ""
            } else {
                context.getString(R.string.device_status_no_permission)
            }
            context.getString(
                if (v) R.string.device_value_true else R.string.device_value_false,
                statusText
            )
        },
        extraContent = {
            Button(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                onClick = requestPermission
            ) {
                val buttonText = when {
                    permissionState.granted -> {
                        stringResource(R.string.device_button_disable_in_app_info)
                    }
                    !permissionState.granted && !permissionState.shouldShowRationale -> {
                        stringResource(R.string.device_button_request_microphone_permission)
                    }
                    else -> {
                        stringResource(R.string.device_button_enable_in_app_info)
                    }
                }
                Text(
                    text = buttonText,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    )
}
