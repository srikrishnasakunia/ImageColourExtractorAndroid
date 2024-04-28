package dev.krishna.imagecolourextractor.util.permission

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PermissionDialog(
    permissionTextProvider: PermissionTextProvider,
    isPermissionPermanentlyDeclined: Boolean,
    onDismiss: () -> Unit,
    onOkClick: () -> Unit,
    onGoToAppSettingsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                if (isPermissionPermanentlyDeclined) {
                    onGoToAppSettingsClick()
                } else {
                    onOkClick()
                }
            }) {
                Text(
                    text = if (isPermissionPermanentlyDeclined) {
                        "Grant Permission"
                    } else {
                        "Ok"
                    }
                )
            }
        },
        modifier = modifier,
        text = {
            Text(text = permissionTextProvider.getDescription(isPermissionPermanentlyDeclined))
        },
        title = {
            Text(text = "Permission Required")
        }
    )
}

fun interface PermissionTextProvider {
    fun getDescription(isPermissionPermanentlyDeclined: Boolean): String
}

class CameraPermissionTextProvider : PermissionTextProvider {
    override fun getDescription(isPermissionPermanentlyDeclined: Boolean): String {
        return if (isPermissionPermanentlyDeclined) {
            "It seems you have permanently declined the Camera Permission." +
                    "Please go to app settings and provide it."
        } else {
            "Camera permission is required by this App." +
                    "Please grant it."
        }
    }
}