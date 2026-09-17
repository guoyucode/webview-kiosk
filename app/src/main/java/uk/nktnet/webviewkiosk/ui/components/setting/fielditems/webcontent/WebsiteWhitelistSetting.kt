package uk.nktnet.webviewkiosk.ui.components.setting.fielditems.webcontent

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import uk.nktnet.webviewkiosk.R
import uk.nktnet.webviewkiosk.config.UserSettings
import uk.nktnet.webviewkiosk.config.UserSettingsKeys
import uk.nktnet.webviewkiosk.ui.components.setting.fields.TextSettingFieldItem
import uk.nktnet.webviewkiosk.utils.validateMultilineRegex

@Composable
fun WebsiteWhitelistSetting() {
    val context = LocalContext.current
    val userSettings = remember { UserSettings(context) }
    val settingKey = UserSettingsKeys.WebContent.WEBSITE_WHITELIST

    TextSettingFieldItem(
        label = stringResource(R.string.web_content_website_whitelist_title),
        infoText = stringResource(R.string.web_content_regex_filter_info),
        placeholder = stringResource(R.string.web_content_website_whitelist_placeholder),
        initialValue = userSettings.websiteWhitelist,
        settingKey = settingKey,
        restricted = userSettings.isRestricted(settingKey),
        isMultiline = true,
        validator = { validateMultilineRegex(it) },
        validationMessage = stringResource(R.string.web_content_regex_filter_invalid),
        onSave = { userSettings.websiteWhitelist = it }
    )
}
