package uk.nktnet.webviewkiosk.config.option

/**
 * 设置界面的显示语言。持久化用枚举 name（英文），label 只做展示，
 * languageTag 喂给 AppCompatDelegate.setApplicationLocales 做应用内 locale 切换。
 * 默认 ENGLISH：保持上游英文界面的原行为，用户显式选中文后才切。
 */
enum class AppLanguageOption(val label: String, val languageTag: String) {
    ENGLISH("English", "en"),
    CHINESE("中文", "zh-CN");

    companion object {
        fun fromString(value: String?): AppLanguageOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: ENGLISH
        }
    }
}
