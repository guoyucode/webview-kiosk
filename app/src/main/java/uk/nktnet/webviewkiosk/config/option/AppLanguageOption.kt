package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

/**
 * 设置界面的显示语言。持久化用枚举 name（英文），label 只做展示，
 * languageTag 喂给 AppCompatDelegate.setApplicationLocales 做应用内 locale 切换。
 * 默认 ENGLISH：保持上游英文界面的原行为，用户显式选中文后才切。
 */
enum class AppLanguageOption(
    val label: String,
    @StringRes val labelRes: Int,
    val languageTag: String,
) {
    // 语言名保持母语形式（任何语言环境下都显示 English / 中文），中文翻译同英文原文
    ENGLISH("English", R.string.app_language_option_english, "en"),
    CHINESE("中文", R.string.app_language_option_chinese, "zh-CN");

    companion object {
        fun fromString(value: String?): AppLanguageOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: ENGLISH
        }
    }
}
