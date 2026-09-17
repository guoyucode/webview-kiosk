package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class AddressBarModeOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    HIDDEN("Hidden", R.string.address_bar_mode_option_hidden),
    HIDDEN_WHEN_LOCKED("Hidden When Locked", R.string.address_bar_mode_option_hidden_when_locked),
    SHOWN("Shown", R.string.address_bar_mode_option_shown);

    companion object {
        fun fromString(value: String?): AddressBarModeOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: HIDDEN_WHEN_LOCKED
        }
    }
}
