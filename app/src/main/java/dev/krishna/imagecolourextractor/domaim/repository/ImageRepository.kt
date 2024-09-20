package dev.krishna.imagecolourextractor.domaim.repository

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap

interface ImageRepository {
    suspend fun getAllImagesFromDirectory(): List<ImageBitmap>
    suspend fun saveImageInDirectory(bitmap: Bitmap): Uri?
}