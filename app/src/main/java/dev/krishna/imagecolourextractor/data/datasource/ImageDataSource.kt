package dev.krishna.imagecolourextractor.data.datasource

import android.content.ContentResolver
import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import dev.krishna.imagecolourextractor.util.image.FOLDER_NAME
import dev.krishna.imagecolourextractor.util.image.getBitmapFromFile
import javax.inject.Inject

class ImageDataSource @Inject constructor(
    private val contentResolver: ContentResolver
) {
    suspend fun fetchAllImages(): List<ImageBitmap> {
        val imageList = mutableListOf<ImageBitmap>()
        val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME
        )
        val selection = "${MediaStore.Images.Media.DATA} like ?"
        val selectionArgs = arrayOf("%$FOLDER_NAME%")

        val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

        contentResolver.query(
            uri,
            projection,
            selection,
            selectionArgs,
            sortOrder
        )?.use {
            val dataIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            while (it.moveToNext()) {
                val filePath = it.getString(dataIndex)
                filePath.getBitmapFromFile()?.let{
                    it1 -> imageList.add(it1.asImageBitmap())
                }
            }
        }

        return imageList
    }


    suspend fun saveImageToDirectory(bitmap: Bitmap): Uri? {
        val values = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Image_${System.currentTimeMillis()}")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, FOLDER_NAME)
                put(MediaStore.Images.Media.IS_PENDING, 1)
            }
        }

        var imageUri: Uri? = null

        try {
            imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            imageUri?.let {
                contentResolver.openOutputStream(it)?.use { stream ->
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    values.clear()
                    values.put(MediaStore.Images.Media.IS_PENDING, 0)
                    contentResolver.update(it, values, null, null)
                }
            }
        } catch (e: Exception) {
            Log.e("Exception", "Exception Thrown while storing Image -> ${e.stackTrace}")
            e.printStackTrace()
        }
        Log.e("SRI","Image Saved Successfully.")
        return imageUri
    }
}