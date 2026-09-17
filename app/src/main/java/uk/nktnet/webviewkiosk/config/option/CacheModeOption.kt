package uk.nktnet.webviewkiosk.config.option

import android.webkit.WebSettings
import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class CacheModeOption(
    val mode: Int,
    val label: String,
    @StringRes val labelRes: Int,
) {
    DEFAULT(WebSettings.LOAD_DEFAULT, "Default", R.string.cache_mode_option_default),
    CACHE_ELSE_NETWORK(WebSettings.LOAD_CACHE_ELSE_NETWORK, "Cache Else Network", R.string.cache_mode_option_cache_else_network),
    NO_CACHE(WebSettings.LOAD_NO_CACHE, "No Cache", R.string.cache_mode_option_no_cache),
    CACHE_ONLY(WebSettings.LOAD_CACHE_ONLY, "Cache Only", R.string.cache_mode_option_cache_only);

    companion object {
        fun fromString(value: String?): CacheModeOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
                || it.mode.toString() == value
            } ?: DEFAULT
        }
    }
}
