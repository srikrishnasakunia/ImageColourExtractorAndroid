package dev.krishna.imagecolourextractor.presentation.camera

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.unit.dp
import dev.krishna.imagecolourextractor.util.image.getAllImagesFromDirectory
import dev.krishna.imagecolourextractor.util.image.getBitmap

@Composable
fun PhotoBottomSheetContent(
    modifier: Modifier = Modifier,
    bitmaps: List<Bitmap>,
    context: Context
) {
    Log.e("SRI", "Bitmap Stack -> ${bitmaps.size}")
    val imagePathList = getAllImagesFromDirectory(context)
    Log.d("DEBUG", imagePathList.size.toString())
    if (imagePathList.isEmpty()) {
        Box(
            modifier = Modifier
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("There are no photos yet.")
        }
    } else {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalItemSpacing = 16.dp,
            contentPadding = PaddingValues(16.dp),
            modifier = modifier
        ) {
            items(imagePathList) { imagePath ->
                imagePath.getBitmap()?.asImageBitmap()?.let {
                    Image(
                        bitmap = it,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }
        }
    }
}