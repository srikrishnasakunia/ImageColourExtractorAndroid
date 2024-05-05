package dev.krishna.imagecolourextractor.util.permission

import android.Manifest
import android.os.Build

val permissionForPhotoGallery =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

val permissionForCamera = Manifest.permission.CAMERA