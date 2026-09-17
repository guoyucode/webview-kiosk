package uk.nktnet.webviewkiosk.ui.components.setting.dialog

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.net.toUri
import uk.nktnet.webviewkiosk.R
import uk.nktnet.webviewkiosk.config.SystemSettings
import uk.nktnet.webviewkiosk.ui.components.webview.HistoryDialog
import uk.nktnet.webviewkiosk.utils.IconUtils
import uk.nktnet.webviewkiosk.utils.validateUrl

@Composable
fun CreateShortcutDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit
) {
    if (!showDialog) {
        return
    }

    val context = LocalContext.current
    val systemSettings = remember { SystemSettings(context) }

    var shortLabel by remember { mutableStateOf("") }
    var longLabel by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }

    var shortLabelError by remember { mutableStateOf<String?>(null) }
    var longLabelError by remember { mutableStateOf<String?>(null) }
    var urlError by remember { mutableStateOf<String?>(null) }
    var isOpenHistoryDialog by remember { mutableStateOf(false) }

    var generatedIcon by remember {
        mutableStateOf(IconUtils.buildLetterIcon(shortLabel))
    }

    val canCreate = (
        shortLabelError == null
        && longLabelError == null
        && urlError == null
        && shortLabel.isNotBlank()
        && longLabel.isNotBlank()
        && url.isNotBlank()
    )

    fun setShortLabel(value: String) {
        shortLabel = value
        shortLabelError = validateShortLabel(context, value)
        generatedIcon = IconUtils.buildLetterIcon(value)
    }

    fun setUrl(value: String) {
        val trimmed = value.trim()
        url = trimmed
        urlError = if (!validateUrl(trimmed)) {
            context.getString(R.string.setting_common_invalid_url)
        } else {
            null
        }
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.setting_common_create_shortcut)) },
        text = {
            Column (
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                SimpleOutlinedTextField(
                    value = shortLabel,
                    onValueChange = {
                        setShortLabel(it)
                    },
                    label = stringResource(R.string.setting_common_short_label),
                    error = shortLabelError
                )

                Spacer(modifier = Modifier.height(4.dp))

                SimpleOutlinedTextField(
                    value = longLabel,
                    onValueChange = {
                        longLabel = it
                        longLabelError = validateLongLabel(context, it)
                    },
                    label = stringResource(R.string.setting_common_long_label),
                    error = longLabelError
                )

                Spacer(modifier = Modifier.height(4.dp))

                SimpleOutlinedTextField(
                    value = url,
                    onValueChange = {
                        setUrl(it)
                    },
                    label = stringResource(R.string.setting_common_url_label),
                    error = urlError
                )

                Button(
                    onClick = {
                        setUrl(systemSettings.currentUrl)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = stringResource(R.string.setting_common_use_current_url),
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                        Text(
                            text = systemSettings.currentUrl,
                            style = MaterialTheme.typography.bodySmall,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
                Button(
                    onClick = { isOpenHistoryDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(top = 6.dp),
                    shape = RoundedCornerShape(4.dp),
                    colors = ButtonDefaults.buttonColors()
                ) {
                    Text(
                        text = stringResource(R.string.setting_common_select_from_history),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Image(
                    bitmap = generatedIcon.bitmap.asImageBitmap(),
                    contentDescription = "Shortcut preview",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                )
            }
        },
        confirmButton = {
            Button(
                enabled = canCreate,
                onClick = {
                    val safeShortLabel = shortLabel.trim()
                    val safeLongLabel = longLabel.trim()
                    val safeUrl = url.trim()

                    val intent = Intent(Intent.ACTION_VIEW).apply {
                        data = safeUrl.toUri()
                        setPackage(context.packageName)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    }

                    val uniqueId = "${System.currentTimeMillis()}_${safeUrl}"
                    val shortcut = ShortcutInfoCompat.Builder(context, uniqueId)
                        .setShortLabel(safeShortLabel)
                        .setLongLabel(safeLongLabel)
                        .setIcon(generatedIcon.icon)
                        .setAlwaysBadged()
                        .setIntent(intent)
                        .build()

                    ShortcutManagerCompat.requestPinShortcut(
                        context,
                        shortcut,
                        null
                    )
                    onDismiss()
                }
            ) {
                Text(stringResource(R.string.setting_common_create))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.setting_common_cancel))
            }
        }
    )

    HistoryDialog(
        isOpenHistoryDialog,
        { isOpenHistoryDialog = false },
        { item, _ ->
            setUrl(item.url)
        },
        disableCurrent = false,
        highlightCurrent = false,
    )
}

@Composable
private fun SimpleOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    modifier: Modifier = Modifier,
    error: String?
) {
    Column { }
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        singleLine = true,
        modifier = modifier.fillMaxWidth(),
        trailingIcon = {
            IconButton(
                onClick = { onValueChange("") },
                enabled = value.isNotEmpty()
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_clear_24),
                    contentDescription = "Clear"
                )
            }
        }
    )
    if (error != null) {
        Text(
            text = error,
            modifier = Modifier.padding(
                top = 6.dp,
                bottom = 4.dp
            ),
            color = MaterialTheme.colorScheme.error
        )
    }
}

private fun validateLabel(
    context: Context,
    value: String,
    fieldNameRes: Int,
    maxLength: Int
): String? {
    val trimmed = value.trim()

    if (trimmed.isBlank()) {
        return context.getString(
            R.string.setting_common_label_cannot_be_empty,
            context.getString(fieldNameRes)
        )
    }
    if (trimmed.length > maxLength) {
        return context.getString(R.string.setting_common_max_characters, maxLength)
    }

    if (trimmed.any { it.isISOControl() }) {
        return context.getString(
            R.string.setting_common_invalid_chars_in_label,
            context.getString(fieldNameRes)
        )
    }

    return null
}

private fun validateShortLabel(context: Context, value: String): String? {
    return validateLabel(
        context = context,
        value = value,
        fieldNameRes = R.string.setting_common_short_label,
        maxLength = 10
    )
}

private fun validateLongLabel(context: Context, value: String): String? {
    return validateLabel(
        context = context,
        value = value,
        fieldNameRes = R.string.setting_common_long_label,
        maxLength = 25
    )
}
