package uk.nktnet.webviewkiosk.config.mqtt

import androidx.annotation.StringRes
import com.hivemq.client.mqtt.datatypes.MqttQos
import uk.nktnet.webviewkiosk.R

enum class MqttQosOption(
    val code: Int,
    val label: String,
    @StringRes val labelRes: Int,
) {
    AT_MOST_ONCE(0, "At Most Once", R.string.mqtt_qos_option_at_most_once),
    AT_LEAST_ONCE(1, "At Least Once", R.string.mqtt_qos_option_at_least_once),
    EXACTLY_ONCE(2, "Exactly Once", R.string.mqtt_qos_option_exactly_once);

    fun toMqttQos(): MqttQos = MqttQos.fromCode(code) ?: MqttQos.AT_MOST_ONCE

    fun getSettingLabel(): String = "$label ($code)"

    companion object {
        fun fromString(value: String?): MqttQosOption =
            entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
                || it.code.toString() == value
                || it.getSettingLabel() == value
            } ?: AT_MOST_ONCE
    }
}
