package uk.nktnet.webviewkiosk.config.option

import android.view.View
import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class OverScrollModeOption(
    val mode: Int,
    val label: String,
    @StringRes val labelRes: Int,
) {
    ALWAYS(View.OVER_SCROLL_ALWAYS, "Always", R.string.over_scroll_mode_option_always),
    IF_CONTENT_SCROLLS(View.OVER_SCROLL_IF_CONTENT_SCROLLS, "If Content Scrolls", R.string.over_scroll_mode_option_if_content_scrolls),
    NEVER(View.OVER_SCROLL_NEVER, "Never", R.string.over_scroll_mode_option_never);

    companion object {
        fun fromString(value: String?): OverScrollModeOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
                || it.mode.toString() == value
            } ?: IF_CONTENT_SCROLLS
        }
    }
}
