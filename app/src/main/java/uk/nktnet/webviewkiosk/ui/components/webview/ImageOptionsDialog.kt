package uk.nktnet.webviewkiosk.ui.components.webview

import android.content.ClipData
import android.content.Intent
import android.webkit.WebView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.toClipEntry
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import kotlinx.coroutines.launch
import uk.nktnet.webviewkiosk.R
import uk.nktnet.webviewkiosk.config.UserSettings
import uk.nktnet.webviewkiosk.managers.ToastManager
import uk.nktnet.webviewkiosk.states.LockStateSingleton
import uk.nktnet.webviewkiosk.utils.fetchRemoteFileInfo
import uk.nktnet.webviewkiosk.utils.getMimeType
import uk.nktnet.webviewkiosk.utils.safeStartActivity
import uk.nktnet.webviewkiosk.utils.webview.handlers.handleDownloadPrompt

@Composable
fun ImageOptionsDialog(
    webView: WebView,
    imageUrl: String?,
    onDismiss: () -> Unit,
    onOpenImage: (String) -> Unit
) {
    val context = LocalContext.current
    val userSettings = remember { UserSettings(context) }
    val clipboard = LocalClipboard.current
    val scope = rememberCoroutineScope()

    val isLocked by LockStateSingleton.isLocked

    if (imageUrl != null) {
        Dialog(onDismissRequest = onDismiss) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.padding(24.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = imageUrl,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Button(
                        onClick = {
                            scope.launch {
                                val clipData = ClipData.newPlainText(
                                    context.getString(R.string.runtime_clipboard_image_url),
                                    imageUrl
                                )
                                clipboard.setClipEntry(clipData.toClipEntry())
                                onDismiss()
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.runtime_copy_link))
                    }

                    Button(
                        onClick = {
                            onOpenImage(imageUrl)
                            onDismiss()
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(stringResource(R.string.runtime_open_image))
                    }

                    if (!isLocked) {
                        val uri = imageUrl.toUri()
                        if (uri.scheme != "file") {
                            Button(
                                onClick = {
                                    val intent = Intent(Intent.ACTION_VIEW, uri)
                                    safeStartActivity(context, intent)
                                    onDismiss()
                                },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(stringResource(R.string.runtime_open_in_browser))
                            }
                        }

                        Button(
                            onClick = {
                                val intent = Intent(Intent.ACTION_SEND).apply {
                                    type = "text/plain"
                                    putExtra(Intent.EXTRA_TEXT, imageUrl)
                                }
                                val chooser = Intent.createChooser(
                                    intent,
                                    context.getString(R.string.runtime_share_link)
                                )
                                safeStartActivity(context, chooser)
                                onDismiss()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.runtime_share_link))
                        }
                    }

                    if (userSettings.allowFileDownload) {
                        Button(
                            onClick = {
                                scope.launch {
                                    val uri = imageUrl.toUri()
                                    var mimeType = getMimeType(context, uri)
                                    var contentDisposition: String? = null

                                    if (mimeType == null) {
                                        ToastManager.show(
                                            context,
                                            context.getString(R.string.runtime_retrieving_image_details)
                                        )
                                        fetchRemoteFileInfo(imageUrl)?.let { info ->
                                            mimeType = info.mimeType
                                            contentDisposition = info.contentDisposition
                                        }
                                    }

                                    if (mimeType == null) {
                                        mimeType = "image/*"
                                    }

                                    handleDownloadPrompt(
                                        context = context,
                                        webView = webView,
                                        url = imageUrl,
                                        userAgent = null,
                                        contentDisposition = contentDisposition,
                                        mimeType = mimeType
                                    )
                                    onDismiss()
                                }
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(stringResource(R.string.runtime_download_image))
                        }
                    }
                }
            }
        }
    }
}
