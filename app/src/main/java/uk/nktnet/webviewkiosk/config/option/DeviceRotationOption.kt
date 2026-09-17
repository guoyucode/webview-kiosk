package uk.nktnet.webviewkiosk.config.option

import androidx.annotation.StringRes
import uk.nktnet.webviewkiosk.R

enum class DeviceRotationOption(
    val label: String,
    @StringRes val labelRes: Int,
) {
    AUTO("Auto", R.string.device_rotation_option_auto),
    ROTATION_0("0", R.string.device_rotation_option_rotation_0),
    ROTATION_90("90", R.string.device_rotation_option_rotation_90),
    ROTATION_180("180", R.string.device_rotation_option_rotation_180),
    ROTATION_270("270", R.string.device_rotation_option_rotation_270);

    companion object {
        fun fromString(value: String?): DeviceRotationOption {
            return entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
            } ?: AUTO
        }
    }
}
