package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import org.json.JSONArray
import uk.nktnet.webviewkiosk.R

enum class WebviewControlActionOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    NAVIGATION("Navigation", R.string.webview_control_action_option_navigation),
    BACK("Back", R.string.webview_control_action_option_back),
    FORWARD("Forward", R.string.webview_control_action_option_forward),
    REFRESH("Refresh", R.string.webview_control_action_option_refresh),
    HOME("Home", R.string.webview_control_action_option_home),
    HISTORY("History", R.string.webview_control_action_option_history),
    BOOKMARK("Bookmark", R.string.webview_control_action_option_bookmark),
    FILES("Files", R.string.webview_control_action_option_files),
    FIND("Find", R.string.webview_control_action_option_find),
    SCROLL_TOP("Scroll Top", R.string.webview_control_action_option_scroll_top),
    SCROLL_BOT("Scroll Bot", R.string.webview_control_action_option_scroll_bot),
    APPS("Apps", R.string.webview_control_action_option_apps),
    SETTINGS("Settings", R.string.webview_control_action_option_settings),
    LOCK("Lock", R.string.webview_control_action_option_lock),
    UNLOCK("Unlock", R.string.webview_control_action_option_unlock);

    companion object {
        fun itemFromString(value: String?): WebviewControlActionOption? {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            }
        }

        fun getDefaultAddressBarOptions(): List<WebviewControlActionOption> = listOf(
            BACK,
            FORWARD,
            REFRESH,
            HOME,
            HISTORY,
            BOOKMARK,
            FILES,
            FIND,
            SCROLL_TOP,
        )

        fun getDefaultKioskControlPanelOptions(): List<WebviewControlActionOption> = listOf(
            NAVIGATION,
            HOME,
            REFRESH,
            HISTORY,
            BOOKMARK,
            FILES,
            LOCK,
            UNLOCK,
        )

        fun parseFromJsonArray(jsonArray: JSONArray?): List<WebviewControlActionOption> {
            if (jsonArray == null) {
                return WebviewControlActionOption.getDefaultKioskControlPanelOptions()
            }
            return List(jsonArray.length()) { idx ->
                itemFromString(jsonArray.optString(idx))
            }.filterNotNull()
        }
    }
}
