package com.jer.banyumastourismapp.presentation.maps

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.presentation.component.CategoryRow
import com.jer.banyumastourismapp.presentation.component.DestinationCardLandscape
import com.jer.banyumastourismapp.presentation.component.SearchBarForAll
import com.jer.banyumastourismapp.ui.theme.BanyumasTourismAppTheme
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    mapsViewModel: MapsViewModel,
    listDestination: List<Destination>,
    navigateToDetail: () -> Unit
) {

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
        myLocationButtonEnabled = false,
        zoomControlsEnabled = false,
        compassEnabled = true,
        mapToolbarEnabled = true,
        scrollGesturesEnabled = true,
        scrollGesturesEnabledDuringRotateOrZoom = true,
        zoomGesturesEnabled = true,
        rotationGesturesEnabled = true,
        tiltGesturesEnabled = true,
    )

//    LaunchedEffect(key1 = requestLocPermission.permissions) {
//        requestLocPermission.launchMultiplePermissionRequest()
//    }


     Scaffold(
         floatingActionButton = {
             Column {
                 Surface(
                     onClick = {
                         permissionUserLocation()
                     },
                     shape = CircleShape,
                     color = MaterialTheme.colorScheme.background,
                     tonalElevation = 8.dp,
                     modifier = Modifier.size(50.dp)
                 ) {
                     Icon(
                         modifier = Modifier.padding(10.dp),
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
                 Spacer(modifier = Modifier.height(350.dp))
             }
         },
         modifier = Modifier.fillMaxSize()
     ) { innerPadding ->

         ConstraintLayout (
             modifier = Modifier
                 .fillMaxSize()
                 .padding(innerPadding)
         ) {

             val (maps, searchBar, category, listSection) = createRefs()

             GoogleMap(
                 modifier = Modifier
                     .constrainAs(maps) {
                         start.linkTo(parent.start)
                         top.linkTo(parent.top)
                     }
                     .padding(innerPadding)
                     .fillMaxSize(),
                 cameraPositionState = cameraPositionState,
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

             SearchBarForAll(
                 hint = "Search Destination",
                 trailingIsVisible = false,
                 modifier = Modifier
                     .constrainAs(searchBar) {
                         top.linkTo(parent.top)
                     }
                     .padding(30.dp)
             )

             CategoryRow(
                 modifier = Modifier.constrainAs(category) {

                     bottom.linkTo(listSection.top)
                 }
             )

             DestinationListRowForMaps(
                 items = listDestination,
                 onClick = navigateToDetail,
                 modifier = Modifier
                     .constrainAs(listSection) {
                         start.linkTo(parent.start)
                         bottom.linkTo(parent.bottom)
                         width = Dimension.fillToConstraints
                     }
                     .padding(bottom = 100.dp)


             )


         }


    }
}


@Composable
fun DestinationListRowForMaps(
    modifier: Modifier = Modifier,
    items: List<Destination>,
    onClick: () -> Unit
) {

    LazyRow (
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        contentPadding = PaddingValues(start = 30.dp, end = 30.dp, bottom = 30.dp, top = 15.dp),
        modifier = modifier
            .fillMaxWidth()
    ) { items(items) { item ->

        DestinationCardLandscape(
            destination = item,
            modifier = Modifier.width(348.dp),
            onClick = onClick,
            buttonVisibility = true
        )
    }

    }

}




