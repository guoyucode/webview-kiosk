package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class RefreshOnNetworkAvailableOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    ALWAYS("Always", R.string.refresh_on_network_available_option_always),
    ON_PAGE_ERROR("On Page Error", R.string.refresh_on_network_available_option_on_page_error),
    NEVER("Never", R.string.refresh_on_network_available_option_never);

    companion object {
        fun fromString(value: String?): RefreshOnNetworkAvailableOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: ON_PAGE_ERROR
        }
    }
}
