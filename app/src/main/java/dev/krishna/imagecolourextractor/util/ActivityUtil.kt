package dev.krishna.imagecolourextractor.util

import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.core.app.ActivityCompat

fun Context.getActivity(): ComponentActivity? = when(this) {
    is ComponentActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

fun openAppSettings(context: Context) {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", context.packageName, null)
    ).also {
        context.startActivity(it)
    }
}

fun checkIfPermissionIsGranted(
    context: Context,
    permission: String,
): Boolean = context.getActivity()?.let {
    ActivityCompat.shouldShowRequestPermissionRationale(
        it,
        permission
    )
} == true
