package uk.nktnet.webviewkiosk.utils

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import uk.nktnet.webviewkiosk.config.option.AppLanguageOption

/**
 * 应用语言切换。setApplicationLocales 已配置 autoStoreLocales（见 Manifest），
 * 会自行持久化并在冷启动恢复；这里先比对当前 locale 再设置，避免重复触发
 * Activity recreate（MainActivity.onCreate 里也会调用一次做兜底）。
 */
fun applyAppLanguage(language: AppLanguageOption) {
    if (AppCompatDelegate.getApplicationLocales().toLanguageTags() != language.languageTag) {
        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(language.languageTag)
        )
    }
}
