# Project Description
The Main Purpose of this Application is for exploring CameraX Library and integrating ML model to
generate a list of all colors/hexcode present in the captured Image.

## Libraries & APIs used so far.
1. Jetpack Compose for UI.
2. CameraX for Capturing Images.
3. Navigation for Screen Management.
4. Dagger Hilt for DI.
5. MediaStore for Image storing and retrieving.



## Project Structure
1. Navigation Folder contains 2 files. 
   Destination & NavGraph.
   Destination is a sealed class which contains all the destinations.
   NavGraph is responsible for navigating to  different screens and hold applicationContext
   which gets passed to required ComposableFunctions. 

2. Presentation Folder contains all the required Composable Functions for Screens.
   CameraPreview.kt is a basic AndroidView which contains PreviewView of CameraX Lib used for 
   showing Preview of Image before capturing it.
   CameraScreen.kt is the ComposeScreen which opens when we click on Camera on Details Screen and
   shows us the CameraPreview, along with the option to capture the photo & open Clicked 
   Photo Gallery, & option to switch Camera.