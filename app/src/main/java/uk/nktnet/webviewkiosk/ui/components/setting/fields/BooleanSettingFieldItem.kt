package uk.nktnet.webviewkiosk.ui.components.setting.fields

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import uk.nktnet.webviewkiosk.R

@Composable
fun BooleanSettingFieldItem(
    label: String,
    infoText: String,
    initialValue: Boolean,
    settingKey: String,
    restricted: Boolean,
    onSave: (Boolean) -> Unit,
    itemText: (Boolean) -> String = defaultBooleanItemText(),
    extraContent: (@Composable ((setValue: (Boolean) -> Unit) -> Unit))? = null,
) {
    DropdownSettingFieldItem(
        label = label,
        infoText = infoText,
        options = listOf(true, false),
        initialValue = initialValue,
        settingKey = settingKey,
        restricted = restricted,
        onSave = onSave,
        itemText = itemText,
        extraContent = extraContent,
    )
}

@Composable
private fun defaultBooleanItemText(): (Boolean) -> String {
    val context = LocalContext.current
    return { value ->
        context.getString(
            if (value) R.string.setting_common_true else R.string.setting_common_false
        )
    }
}
