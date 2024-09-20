package dev.krishna.imagecolourextractor.presentation.camera.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.krishna.imagecolourextractor.domaim.usecase.GetAllImagesUseCase
import dev.krishna.imagecolourextractor.domaim.usecase.SaveImageUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor(
    //@IoDispatcher private val ioDispatcher: CoroutineContext,
    private val getAllImagesUseCase: GetAllImagesUseCase,
    private val saveImageUseCase: SaveImageUseCase
) :ViewModel() {

    private val _getAllImagesAsBitmapList = MutableStateFlow<List<ImageBitmap>>(emptyList())
    val getAllImagesAsBitmapList: StateFlow<List<ImageBitmap>> =
        _getAllImagesAsBitmapList.asStateFlow()

    private val _showProgressBarBoolean = MutableStateFlow<Boolean>(false)
    val showProgressBarBoolean: StateFlow<Boolean>
        get() = _showProgressBarBoolean.asStateFlow()

    //val imageBitmapToBeSaveInDirectory: Bitmap? = null

    fun getAllImagesFromDirectory() {
        viewModelScope.launch(Dispatchers.IO) {
            _showProgressBarBoolean.emit(true)
            val imagesBitmap = getAllImagesUseCase.invoke()
            withContext(Dispatchers.Main) {
                _showProgressBarBoolean.emit(false)
                _getAllImagesAsBitmapList.emit(imagesBitmap)
                Log.e("SRI", "${System.currentTimeMillis()}")
            }
        }

    }


    fun saveImageInDirectory(bitmap: Bitmap) {
        Log.e("SRI","Image Saving process beings")
        viewModelScope.launch(Dispatchers.IO) {
            _showProgressBarBoolean.emit(true)
            saveImageUseCase.invoke(bitmap)
            _showProgressBarBoolean.emit(false)
        }
    }
}