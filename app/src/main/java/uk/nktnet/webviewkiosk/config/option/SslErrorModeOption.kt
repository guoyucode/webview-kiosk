package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class SslErrorModeOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    BLOCK("Block", R.string.ssl_error_mode_option_block),
    PROMPT("Prompt", R.string.ssl_error_mode_option_prompt),
    PROCEED("Proceed", R.string.ssl_error_mode_option_proceed);

    companion object {
        fun fromString(value: String?): SslErrorModeOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: BLOCK
        }
    }
}
