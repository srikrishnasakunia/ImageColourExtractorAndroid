package dev.krishna.imagecolourextractor.ui.customcompose

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressBar(isLoading:Boolean) {
    Box {
        if (isLoading){
            Log.e("SRI", "Showing ProgressBar")
            CircularProgressIndicator(
                modifier = Modifier
                    .size(50.dp)
                    .align(Alignment.Center)
            )
        }
    }
}

@Preview
@Composable
private fun CircularProgressBarPrev() {
    CircularProgressBar(isLoading = true)
    
}