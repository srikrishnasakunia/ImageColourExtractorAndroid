package dev.krishna.imagecolourextractor.util.image

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import java.io.File

val FOLDER_NAME = Environment.DIRECTORY_PICTURES + File.separator + "Imager"
fun saveImageToGallery(
    context: Context,
    bitmap: Bitmap,
    imageName: String
): Uri? {

    val values = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, imageName)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            put(MediaStore.Images.Media.RELATIVE_PATH, FOLDER_NAME)
            put(MediaStore.Images.Media.IS_PENDING, 1)
        }
    }

    val contentResolver = context.contentResolver
    var imageUri: Uri? = null

    try {
        imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        imageUri?.let {
            contentResolver.openOutputStream(it)?.use { stream->
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
    return imageUri
}


fun getAllImagesFromDirectory(context: Context): List<String> {
    val imageList = mutableListOf<String>()
    val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
    val projection = arrayOf (
        MediaStore.Images.Media.DATA,
        MediaStore.Images.Media.DISPLAY_NAME
    )
    val selection = "${MediaStore.Images.Media.DATA} like ?"
    val selectionArgs = arrayOf("%$FOLDER_NAME%")

    val sortOrder = "${MediaStore.Images.Media.DATE_ADDED} DESC"

    context.contentResolver.query(
        uri,
        projection,
        selection,
        selectionArgs,
        sortOrder
    )?.use {
        val dataIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        while (it.moveToNext()) {
            val filePath = it.getString(dataIndex)
            imageList.add(filePath)
        }
    }

    return imageList
}

fun String.getBitmapFromFile(): Bitmap? {
    val imageFile = File(this)
    var imageBitmap: Bitmap? = null
    if (imageFile.exists()){
        imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
    }
    return imageBitmap
}