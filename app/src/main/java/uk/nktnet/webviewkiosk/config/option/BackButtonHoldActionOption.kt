package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class BackButtonHoldActionOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    OPEN_KIOSK_CONTROL_PANEL("Open Kiosk Control Panel", R.string.back_button_hold_action_option_open_kiosk_control_panel),
    GO_HOME("Go Home", R.string.back_button_hold_action_option_go_home),
    DISABLED("Disabled", R.string.back_button_hold_action_option_disabled);

    companion object {
        fun fromString(value: String?): BackButtonHoldActionOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: OPEN_KIOSK_CONTROL_PANEL
        }
    }
}
