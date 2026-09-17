package uk.nktnet.webviewkiosk.ui.components.setting.mqtt

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hivemq.client.mqtt.MqttClientState
import kotlinx.coroutines.delay
import uk.nktnet.webviewkiosk.R
import uk.nktnet.webviewkiosk.config.UserSettings
import uk.nktnet.webviewkiosk.config.remote.outbound.OutboundDisconnectingEvent
import uk.nktnet.webviewkiosk.managers.MqttManager
import uk.nktnet.webviewkiosk.managers.ToastManager
import kotlin.math.max
import kotlin.time.Duration.Companion.milliseconds

@Composable
fun MqttControlButtons() {
    val context = LocalContext.current
    val userSettings = remember { UserSettings(context) }
    var mqttClientState by remember { mutableStateOf(MqttManager.getState()) }

    var isConnecting by remember { mutableStateOf(false) }
    var isDisconnecting by remember { mutableStateOf(false) }
    var isRestarting by remember { mutableStateOf(false) }

    LaunchedEffect(
        isConnecting,
        isDisconnecting,
        isRestarting,
    ) {
        while (true) {
            mqttClientState = MqttManager.getState()
            delay(500.milliseconds)
        }
    }

    mqttClientState.let { state ->
        val statusColor = when (state) {
            MqttClientState.CONNECTING,
            MqttClientState.CONNECTING_RECONNECT -> MaterialTheme.colorScheme.tertiary
            MqttClientState.CONNECTED -> MaterialTheme.colorScheme.primary
            MqttClientState.DISCONNECTED_RECONNECT -> MaterialTheme.colorScheme.secondary
            MqttClientState.DISCONNECTED -> MaterialTheme.colorScheme.error
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 4.dp)
                    .border(
                        width = 1.dp,
                        color = statusColor,
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Text(
                        text = stringResource(R.string.mqtt_status_label),
                        style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = state.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = statusColor
                    )
                }
            }

            if (state.isConnected) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Button(
                        enabled = !isDisconnecting,
                        onClick = {
                            isDisconnecting = true
                            MqttManager.disconnect(
                                cause = OutboundDisconnectingEvent.DisconnectCause.USER_INITIATED_DISCONNECT,
                                onDisconnected = {
                                    isDisconnecting = false
                                    ToastManager.show(
                                        context,
                                        context.getString(R.string.mqtt_toast_disconnected_success)
                                    )
                                },
                                onError = { err ->
                                    isDisconnecting = false
                                    ToastManager.show(
                                        context,
                                        context.getString(
                                            R.string.mqtt_toast_disconnected_failed,
                                            err
                                        )
                                    )
                                },
                            )
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer,
                            contentColor = MaterialTheme.colorScheme.onErrorContainer
                        )
                    ) {
                        Text(stringResource(R.string.mqtt_disconnect))
                    }

                    Button(
                        enabled = !isRestarting,
                        onClick = {
                            isRestarting = true
                            MqttManager.disconnect(
                                cause = OutboundDisconnectingEvent.DisconnectCause.USER_INITIATED_RESTART,
                                onDisconnected = {
                                    MqttManager.connect(
                                        context.applicationContext,
                                        onConnected = {
                                            isRestarting = false
                                            ToastManager.show(
                                                context,
                                                context.getString(R.string.mqtt_toast_restarted_success)
                                            )
                                        },
                                        onError = {
                                            isRestarting = false
                                            ToastManager.show(
                                                context,
                                                context.getString(
                                                    R.string.mqtt_toast_error_connecting,
                                                    it
                                                )
                                            )
                                        }
                                    )
                                },
                                onError = {
                                    isRestarting = false
                                    ToastManager.show(
                                        context,
                                        context.getString(
                                            R.string.mqtt_toast_error_disconnecting,
                                            it
                                        )
                                    )
                                }
                            )

                        },
                        modifier = Modifier.weight(1f)
                    ) { Text(stringResource(R.string.mqtt_restart)) }
                }
            } else if (
                state in listOf(
                    MqttClientState.CONNECTING,
                    MqttClientState.CONNECTING_RECONNECT,
                    MqttClientState.DISCONNECTED_RECONNECT
                )
            ) {
                Button(
                    onClick = {
                        val res = MqttManager.cancelConnect()
                        val maxWait = max(
                            userSettings.mqttSocketConnectTimeout,
                            userSettings.mqttConnectTimeout
                        )
                        if (res) {
                            ToastManager.show(
                                context,
                                context.getString(R.string.mqtt_toast_cancelling, maxWait)
                            )
                        } else {
                            ToastManager.show(
                                context,
                                context.getString(
                                    R.string.mqtt_toast_already_cancelling,
                                    maxWait
                                )
                            )
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.mqtt_cancel_connection))
                }
            } else {
                Button(
                    enabled = !isConnecting,
                    onClick = {
                        isConnecting = true
                        MqttManager.connect(
                            context.applicationContext,
                            onConnected = {
                                isConnecting = false
                                ToastManager.show(
                                    context,
                                    context.getString(R.string.mqtt_toast_connected_success)
                                )
                            },
                            onError = {
                                isConnecting = false
                                ToastManager.show(
                                    context,
                                    context.getString(R.string.mqtt_toast_connection_failed, it)
                                )
                            }
                        )
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.mqtt_connect))
                }
            }
        }
    }
}
