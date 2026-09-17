package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class ImmersiveModeOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    ALWAYS_ON("Always On", R.string.immersive_mode_option_always_on),
    ALWAYS_OFF("Always Off", R.string.immersive_mode_option_always_off),
    ONLY_WHEN_LOCKED("Only When Locked", R.string.immersive_mode_option_only_when_locked);

    companion object {
        fun fromString(value: String?): ImmersiveModeOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: ONLY_WHEN_LOCKED
        }
    }
}
