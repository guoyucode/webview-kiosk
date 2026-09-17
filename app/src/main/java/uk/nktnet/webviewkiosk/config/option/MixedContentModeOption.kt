package uk.nktnet.webviewkiosk.config.option

import android.webkit.WebSettings
import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class MixedContentModeOption(
    val mode: Int,
    val label: String,
    @StringRes val labelRes: Int,
) {
    NEVER_ALLOW(WebSettings.MIXED_CONTENT_NEVER_ALLOW, "Never Allow", R.string.mixed_content_mode_option_never_allow),
    COMPATIBILITY_MODE(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE, "Compatibility Mode", R.string.mixed_content_mode_option_compatibility_mode),
    ALWAYS_ALLOW(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW, "Always Allow", R.string.mixed_content_mode_option_always_allow);

    companion object {
        fun fromString(value: String?): MixedContentModeOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
                || it.mode.toString() == value
            } ?: NEVER_ALLOW
        }
    }
}
