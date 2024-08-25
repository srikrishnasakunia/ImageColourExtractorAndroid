package dev.krishna.imagecolourextractor.presentation.camera.viewmodel

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class CameraViewModel @Inject constructor() :ViewModel() {

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    fun onTakePhoto(bitmap: Bitmap) {
        Log.e("SRI", "Bitmap length = ${bitmaps.value.size}")
        Log.e("SRI", "Image -> ${bitmap}")
        _bitmaps.value+=(bitmap)
    }
}