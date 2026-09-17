package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeContent
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.safeGestures
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.runtime.Composable
import uk.nktnet.webviewkiosk.R

enum class WebViewInsetOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    NONE("None", R.string.webview_inset_option_none),
    STATUS_BARS("Status Bars", R.string.webview_inset_option_status_bars),
    NAVIGATION_BARS("Navigation Bars", R.string.webview_inset_option_navigation_bars),
    SYSTEM_BARS("System Bars", R.string.webview_inset_option_system_bars),
    DISPLAY_CUTOUT("Display Cutout", R.string.webview_inset_option_display_cutout),
    SAFE_DRAWING("Safe Drawing", R.string.webview_inset_option_safe_drawing),
    SAFE_GESTURES("Safe Gestures", R.string.webview_inset_option_safe_gestures),
    SAFE_CONTENT("Safe Content", R.string.webview_inset_option_safe_content);

    @Composable
    fun toWindowInsets(): WindowInsets = when (this) {
        NONE -> WindowInsets()
        STATUS_BARS -> WindowInsets.statusBars
        NAVIGATION_BARS -> WindowInsets.navigationBars
        SYSTEM_BARS -> WindowInsets.systemBars
        DISPLAY_CUTOUT -> WindowInsets.displayCutout
        SAFE_DRAWING -> WindowInsets.safeDrawing
        SAFE_GESTURES -> WindowInsets.safeGestures
        SAFE_CONTENT -> WindowInsets.safeContent
    }

    companion object {
        fun fromString(value: String?): WebViewInsetOption =
            entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: SYSTEM_BARS
    }
}
