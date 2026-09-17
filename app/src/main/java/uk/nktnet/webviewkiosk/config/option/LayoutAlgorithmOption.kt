package uk.nktnet.webviewkiosk.config.option

import android.webkit.WebSettings
import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class LayoutAlgorithmOption(
    val algorithm: WebSettings.LayoutAlgorithm,
    val label: String,
    @StringRes val labelRes: Int,
) {
    NORMAL(WebSettings.LayoutAlgorithm.NORMAL, "Normal", R.string.layout_algorithm_option_normal),
    @Suppress("DEPRECATION")
    SINGLE_COLUMN(WebSettings.LayoutAlgorithm.SINGLE_COLUMN, "Single Column", R.string.layout_algorithm_option_single_column),
    @Suppress("DEPRECATION")
    NARROW_COLUMNS(WebSettings.LayoutAlgorithm.NARROW_COLUMNS, "Narrow Columns", R.string.layout_algorithm_option_narrow_columns),
    TEXT_AUTOSIZING(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING, "Text Autosizing", R.string.layout_algorithm_option_text_autosizing);

    companion object {
        fun fromString(value: String?): LayoutAlgorithmOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: NORMAL
        }
    }
}
