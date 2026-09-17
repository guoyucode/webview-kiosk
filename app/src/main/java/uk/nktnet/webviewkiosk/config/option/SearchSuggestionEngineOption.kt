package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class SearchSuggestionEngineOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    NONE("None", R.string.search_suggestion_engine_option_none),
    GOOGLE("Google", R.string.search_suggestion_engine_option_google),
    DUCKDUCKGO("DuckDuckGo", R.string.search_suggestion_engine_option_duckduckgo),
    YAHOO("Yahoo", R.string.search_suggestion_engine_option_yahoo);

    companion object {
        fun fromString(value: String?): SearchSuggestionEngineOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: NONE
        }
    }
}
