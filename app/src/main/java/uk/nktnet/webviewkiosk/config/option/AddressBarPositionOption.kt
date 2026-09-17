package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class AddressBarPositionOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    TOP("Top", R.string.address_bar_position_option_top),
    BOTTOM("Bottom", R.string.address_bar_position_option_bottom);

    companion object {
        fun fromString(value: String?): AddressBarPositionOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: TOP
        }
    }
}
