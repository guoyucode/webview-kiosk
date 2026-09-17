package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class ThemeOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    SYSTEM("System", R.string.theme_option_system),
    DARK("Dark", R.string.theme_option_dark),
    LIGHT("Light", R.string.theme_option_light);

    companion object {
        fun fromString(value: String?): ThemeOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: SYSTEM
        }
    }
}
