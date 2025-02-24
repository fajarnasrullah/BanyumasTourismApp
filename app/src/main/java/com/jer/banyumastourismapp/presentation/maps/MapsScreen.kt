package com.jer.banyumastourismapp.presentation.maps

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.jer.banyumastourismapp.R
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen(modifier: Modifier = Modifier, mapsViewModel: MapsViewModel) {

    val jakarta = LatLng(-6.200000, 106.816666)
    val banyumas = LatLng(-7.51417, 109.29417)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(banyumas, 10f)

    }

    var isGrantedPermission by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val userLocation by mapsViewModel.userLocation
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            mapsViewModel.fetchUserLocation(context, fusedLocationClient)
            isGrantedPermission = true
        } else {
            isGrantedPermission = false
            Timber.e("Location permission was denied by the user.")
        }
    }


    fun permissionUserLocation() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
                mapsViewModel.fetchUserLocation(context, fusedLocationClient)
                userLocation?.let {
                    cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 20f)
                }
                isGrantedPermission = true
            }
            else -> {
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
            }
        }
    }

    LaunchedEffect(Unit) {
        permissionUserLocation()
    }

    val mapProperties = MapProperties(
        isMyLocationEnabled = isGrantedPermission,
        isTrafficEnabled = true,
        isBuildingEnabled = true,
        isIndoorEnabled = true,
        mapType = MapType.HYBRID
    )

    val uiSettings = MapUiSettings(
//        myLocationButtonEnabled = true,
        compassEnabled = true,
        mapToolbarEnabled = true,
        scrollGesturesEnabled = true,
        scrollGesturesEnabledDuringRotateOrZoom = true,
        zoomControlsEnabled = true,
        zoomGesturesEnabled = true,
        rotationGesturesEnabled = true,
        tiltGesturesEnabled = true,
    )

//    LaunchedEffect(key1 = requestLocPermission.permissions) {
//        requestLocPermission.launchMultiplePermissionRequest()
//    }


     Scaffold(
         floatingActionButton = {
             Surface (
                 onClick = {
                     permissionUserLocation()
                 },
                 shape = CircleShape,
                 color = MaterialTheme.colorScheme.background,
                 tonalElevation = 8.dp,
                 modifier = Modifier.size(50.dp)
             ) {
                 Icon(
                     painter =
                     if (isGrantedPermission) {
                         painterResource(R.drawable.location_point_icon)
                     } else {
                         painterResource(R.drawable.location_disabled_icon)
                     },
                     contentDescription = null,
                     tint =
                         if (isGrantedPermission) {
                             MaterialTheme.colorScheme.primaryContainer
                         } else {
                             MaterialTheme.colorScheme.errorContainer
                         }
//                     when (PackageManager.PERMISSION_GRANTED) {
//                         ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) -> {
//                             MaterialTheme.colorScheme.primaryContainer
//                         }
//                         else -> {
//                             MaterialTheme.colorScheme.outline
//                         }
//                     }
                 )
             }
         },
         modifier = Modifier.fillMaxSize()
     ) { innerPadding ->

        GoogleMap(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxWidth()
                .fillMaxHeight(fraction = 0.8f),
            cameraPositionState = cameraPositionState
            ,
            properties = mapProperties,
            uiSettings = uiSettings,
            onMyLocationButtonClick = { true }

        ) {
            userLocation?.let {
                Marker(
                    state = MarkerState(position = it),
                    title = "Your Location",
                    snippet = "This is where you are currently located."
                )
                cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 10f)}
        }
    }
}