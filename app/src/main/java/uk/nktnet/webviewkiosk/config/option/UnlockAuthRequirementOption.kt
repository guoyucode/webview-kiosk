package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class UnlockAuthRequirementOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    DEFAULT("Default", R.string.unlock_auth_requirement_option_default),
    OFF("Off", R.string.unlock_auth_requirement_option_off),
    REQUIRE("Require", R.string.unlock_auth_requirement_option_require);

    companion object {
        fun fromString(value: String?): UnlockAuthRequirementOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: DEFAULT
        }
    }
}
