package dev.krishna.imagecolourextractor.data.repository

import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import dev.krishna.imagecolourextractor.data.datasource.ImageDataSource
import dev.krishna.imagecolourextractor.domaim.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val dataSource: ImageDataSource
):ImageRepository {
    override suspend fun getAllImagesFromDirectory(): List<ImageBitmap> {
        return dataSource.fetchAllImages()
    }

    override suspend fun saveImageInDirectory(bitmap: Bitmap): Uri? {
        return dataSource.saveImageToDirectory(bitmap)
    }
}