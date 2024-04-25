package dev.krishna.imagecolourextractor.ui.customcompose

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.krishna.imagecolourextractor.R

@Composable
fun SquareImageButton(
    imageResId: Int,
    text: String,
    buttonSizeInDp: Int,
    imageSizeInDp:Int,
    onClickAction: () -> Unit
) {
    Button(
        onClick = { onClickAction() },
        modifier = Modifier
            .size(buttonSizeInDp.dp)
            .background(Color.White),
        contentPadding = PaddingValues(dimensionResource(id = R.dimen.padding_8_dp)),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 2.dp
        ),
        shape = MaterialTheme.shapes.extraSmall,
        colors = ButtonDefaults.buttonColors(Color.Transparent),
        border = BorderStroke(
            width = dimensionResource(id = R.dimen.border_stroke_2_dp),
            color = colorResource(id = R.color.cobalt_blue))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResId),
                contentDescription = null,
                modifier = Modifier.size(imageSizeInDp.dp)
            )

            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.valhalla),
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.robotocondensed_mediumitalic)),
                fontSize = 35.sp,

            )
        }
    }
}

@Preview
@Composable
private fun PreviewImageButton() {
    SquareImageButton(
        imageResId = R.drawable.ic_camera,
        text = "Camera",
        buttonSizeInDp = 180,
        imageSizeInDp = 105,
    ) {

    }
}