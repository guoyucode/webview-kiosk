package uk.nktnet.webviewkiosk.ui.components.setting.fielditems.mqtt.connection

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import uk.nktnet.webviewkiosk.R
import uk.nktnet.webviewkiosk.config.UserSettings
import uk.nktnet.webviewkiosk.config.UserSettingsKeys
import uk.nktnet.webviewkiosk.ui.components.setting.fields.TextSettingFieldItem

@Composable
fun MqttWebSocketServerPathSetting() {
    val context = LocalContext.current
    val userSettings = remember { UserSettings(context) }
    val settingKey = UserSettingsKeys.Mqtt.Connection.WEBSOCKET_SERVER_PATH

    TextSettingFieldItem(
        label = stringResource(R.string.mqtt_connection_websocket_server_path_title),
        infoText = stringResource(R.string.mqtt_connection_websocket_server_path_info),
        placeholder = stringResource(R.string.mqtt_connection_websocket_server_path_placeholder),
        initialValue = userSettings.mqttWebSocketServerPath,
        settingKey = settingKey,
        restricted = userSettings.isRestricted(settingKey),
        validator = { it.isEmpty() || it.startsWith('/') },
        validationMessage = stringResource(R.string.mqtt_connection_websocket_server_path_invalid),
        isMultiline = false,
        onSave = { userSettings.mqttWebSocketServerPath = it },
    )
}
