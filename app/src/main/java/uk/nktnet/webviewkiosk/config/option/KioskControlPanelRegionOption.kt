package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class KioskControlPanelRegionOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    TOP_LEFT("Top Left", R.string.kiosk_control_panel_region_option_top_left),
    TOP_RIGHT("Top Right", R.string.kiosk_control_panel_region_option_top_right),
    BOTTOM_LEFT("Bottom Left", R.string.kiosk_control_panel_region_option_bottom_left),
    BOTTOM_RIGHT("Bottom Right", R.string.kiosk_control_panel_region_option_bottom_right),
    TOP("Top", R.string.kiosk_control_panel_region_option_top),
    BOTTOM("Bottom", R.string.kiosk_control_panel_region_option_bottom),
    FULL("Full", R.string.kiosk_control_panel_region_option_full),
    DISABLED("Disabled", R.string.kiosk_control_panel_region_option_disabled);

    companion object {
        fun fromString(value: String?): KioskControlPanelRegionOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: TOP_LEFT
        }
    }
}
