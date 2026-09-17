package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class OverrideUrlLoadingBlockActionOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    SHOW_BLOCK_PAGE("Show Block Page", R.string.override_url_loading_block_action_option_show_block_page),
    PREVENT_NAVIGATION("Prevent Navigation", R.string.override_url_loading_block_action_option_prevent_navigation),
    SHOW_TOAST("Show Toast", R.string.override_url_loading_block_action_option_show_toast);

    companion object {
        fun fromString(value: String?): OverrideUrlLoadingBlockActionOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: SHOW_BLOCK_PAGE
        }
    }
}
