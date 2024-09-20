package dev.krishna.imagecolourextractor.domaim.usecase

import androidx.compose.ui.graphics.ImageBitmap
import dev.krishna.imagecolourextractor.domaim.repository.ImageRepository

class GetAllImagesUseCase(
    private val repository: ImageRepository
) {
    suspend operator fun invoke(): List<ImageBitmap> {
        return repository.getAllImagesFromDirectory()
    }
}

