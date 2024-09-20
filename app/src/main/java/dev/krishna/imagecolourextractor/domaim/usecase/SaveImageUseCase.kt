package dev.krishna.imagecolourextractor.domaim.usecase

import android.graphics.Bitmap
import android.net.Uri
import dev.krishna.imagecolourextractor.domaim.repository.ImageRepository

class SaveImageUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(bitmap: Bitmap): Uri? {
        return repository.saveImageInDirectory(bitmap)
    }
}