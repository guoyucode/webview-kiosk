package uk.nktnet.webviewkiosk.ui.components.setting.fielditems.webbrowsing

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import uk.nktnet.webviewkiosk.R
import uk.nktnet.webviewkiosk.config.UserSettings
import uk.nktnet.webviewkiosk.config.UserSettingsKeys
import uk.nktnet.webviewkiosk.config.option.KioskControlPanelRegionOption
import uk.nktnet.webviewkiosk.ui.components.setting.fields.DropdownSettingFieldItem
import uk.nktnet.webviewkiosk.utils.canDisableKioskControlPanelRegion

@Composable
fun KioskControlPanelRegionSetting() {
    val context = LocalContext.current
    val userSettings = remember { UserSettings(context) }
    val settingKey = UserSettingsKeys.WebBrowsing.KIOSK_CONTROL_PANEL_REGION

    DropdownSettingFieldItem(
        label = stringResource(R.string.web_browsing_kiosk_control_panel_region_title),
        infoText = stringResource(R.string.web_browsing_kiosk_control_panel_region_info),
        options = KioskControlPanelRegionOption.entries,
        initialValue = userSettings.kioskControlPanelRegion,
        settingKey = settingKey,
        restricted = userSettings.isRestricted(settingKey),
        onSave = { userSettings.kioskControlPanelRegion = it },
        validator = {
            it != KioskControlPanelRegionOption.DISABLED
            || canDisableKioskControlPanelRegion(userSettings)
        },
        validationMessage = stringResource(R.string.web_browsing_kiosk_control_panel_region_validation),
        itemText = {
            if (
                it == KioskControlPanelRegionOption.DISABLED
                && !canDisableKioskControlPanelRegion(userSettings)
            ) {
                context.getString(R.string.web_browsing_kiosk_control_panel_region_disabled_item)
            } else {
                context.getString(it.labelRes)
            }
        }
    )
}
