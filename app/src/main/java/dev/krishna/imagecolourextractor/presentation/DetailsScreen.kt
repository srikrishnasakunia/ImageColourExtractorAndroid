package dev.krishna.imagecolourextractor.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.krishna.imagecolourextractor.R
import dev.krishna.imagecolourextractor.ui.customcompose.SquareImageButton
import dev.krishna.imagecolourextractor.util.checkIfPermissionIsGranted
import dev.krishna.imagecolourextractor.util.getActivity
import dev.krishna.imagecolourextractor.util.openAppSettings
import dev.krishna.imagecolourextractor.util.permission.CameraPermissionTextProvider
import dev.krishna.imagecolourextractor.util.permission.PermissionDialog
import dev.krishna.imagecolourextractor.util.permission.PermissionViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController
) {
    val permissionViewModel = viewModel<PermissionViewModel>()
    val dialogueQueue = permissionViewModel.visiblePermissionDialogQueue
    val context = LocalContext.current

    val permissionForPhotoGallery =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            arrayOf(
                Manifest.permission.READ_MEDIA_IMAGES,
                Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
            )
        } else {
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }

    val cameraPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            permissionViewModel.onPermissionResult(
                permission = Manifest.permission.CAMERA,
                isGranted = isGranted
            )
        }
    )

    val imageGalleryPermissionResultLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions(),
        onResult = { perms ->
            permissionForPhotoGallery.forEach { permission ->
                permissionViewModel.onPermissionResult(
                    permission = permission,
                    isGranted = perms[permission] == true
                )
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.details_screen_title),
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.ExtraBold,
                        fontFamily = FontFamily(Font(R.font.robotocondensed_mediumitalic)),
                        color = Color.Black
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Gray,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onSecondary
                )
            )
        },
        content = {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_16_dp)),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                SquareImageButton(
                    imageResId = R.drawable.ic_camera,
                    text = "Camera",
                    buttonSizeInDp = 160,
                    imageSizeInDp = 100
                ) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.CAMERA
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        navController.navigate("Screen1")
                    } else {
                        cameraPermissionResultLauncher.launch(
                            Manifest.permission.CAMERA
                        )
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                SquareImageButton(
                    imageResId = R.drawable.ic_gallery,
                    text = "Gallery",
                    buttonSizeInDp = 160,
                    imageSizeInDp = 100
                ) {
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        try {
                            val mediaPicker = context.getActivity()?.registerForActivityResult(
                                ActivityResultContracts.PickVisualMedia()
                            ) { uri ->
                                if (uri != null) {
                                    Log.d("ASX", "Selected URI: $uri")
                                } else {
                                    Log.d("ASX", "No URI selected")
                                }
                            }
                            mediaPicker?.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        } catch (e: Exception) {
                            Log.e("MediaPickerException", "Exception found: $e")
                        }
                    } else {
                        imageGalleryPermissionResultLauncher.launch(
                            permissionForPhotoGallery
                        )
                    }
                }
            }
        }
    )

    dialogueQueue
        .reversed()
        .forEach { permission ->
            PermissionDialog(
                permissionTextProvider = when (permission) {
                    Manifest.permission.CAMERA -> {
                        CameraPermissionTextProvider()
                    }

                    else -> return@forEach
                },
                isPermissionPermanentlyDeclined = !checkIfPermissionIsGranted(context, permission),
                onDismiss = permissionViewModel::dismissDialog,
                onOkClick = { navController.navigate("Screen1") },
                onGoToAppSettingsClick = {
                    openAppSettings(context)
                }
            )
        }

}


@Preview
@Composable
private fun PreviewDetailScreen() {
    val navController = rememberNavController()
    DetailsScreen(navController = navController)
}



