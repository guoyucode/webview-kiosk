package uk.nktnet.webviewkiosk.config.mqtt

import androidx.annotation.StringRes
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5RetainHandling
import uk.nktnet.webviewkiosk.R

enum class MqttRetainHandlingOption(
    val code: Int,
    val label: String,
    @StringRes val labelRes: Int,
) {
    SEND(0, "Send", R.string.mqtt_retain_handling_option_send),
    SEND_IF_SUBSCRIPTION_DOES_NOT_EXIST(1, "Send If Subscription Does Not Exist", R.string.mqtt_retain_handling_option_send_if_subscription_does_not_exist),
    DO_NOT_SEND(2, "Do Not Send", R.string.mqtt_retain_handling_option_do_not_send);

    fun toMqttRetainHandling(): Mqtt5RetainHandling =
        Mqtt5RetainHandling.fromCode(code) ?: Mqtt5RetainHandling.DO_NOT_SEND

    fun getSettingLabel(): String = "$label ($code)"

    companion object {
        fun fromString(value: String?): MqttRetainHandlingOption =
            entries.find {
                it.name.equals(value, ignoreCase = true)
                || it.label.equals(value, ignoreCase = true)
                || it.code.toString() == value
                || it.getSettingLabel() == value
            } ?: DO_NOT_SEND
    }
}
