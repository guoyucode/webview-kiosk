package uk.nktnet.webviewkiosk.ui.components.setting.fielditems.appearance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import uk.nktnet.webviewkiosk.R
import uk.nktnet.webviewkiosk.config.UserSettings
import uk.nktnet.webviewkiosk.config.UserSettingsKeys
import uk.nktnet.webviewkiosk.config.option.AppLanguageOption
import uk.nktnet.webviewkiosk.ui.components.setting.fields.DropdownSettingFieldItem
import uk.nktnet.webviewkiosk.utils.applyAppLanguage

@Composable
fun LanguageSetting() {
    val context = LocalContext.current
    val userSettings = remember { UserSettings(context) }
    val settingKey = UserSettingsKeys.Appearance.APP_LANGUAGE

    DropdownSettingFieldItem(
        label = stringResource(R.string.appearance_app_language_title),
        infoText = stringResource(R.string.appearance_app_language_info),
        options = AppLanguageOption.entries,
        initialValue = userSettings.appLanguage,
        settingKey = settingKey,
        restricted = userSettings.isRestricted(settingKey),
        itemText = { it.label },
        onSave = {
            userSettings.appLanguage = it
            applyAppLanguage(it)
        },
    )
}
