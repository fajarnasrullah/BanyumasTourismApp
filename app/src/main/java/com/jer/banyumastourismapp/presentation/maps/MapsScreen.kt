package com.jer.banyumastourismapp.presentation.maps

import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.content.ContextCompat
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.jer.banyumastourismapp.R
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.presentation.component.CategoryRow
import com.jer.banyumastourismapp.presentation.component.DestinationCardLandscape
import com.jer.banyumastourismapp.presentation.component.SearchBarForAll
import com.jer.banyumastourismapp.presentation.listDestination
import com.jer.banyumastourismapp.presentation.utils.BitmapParameters
import com.jer.banyumastourismapp.presentation.utils.vectorToBitmap
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    mapsViewModel: MapsViewModel,
//    listDestination: LazyPagingItems<Destination>,
    navigateToDetail: (Destination) -> Unit
) {

    val jakarta = LatLng(-6.200000, 106.816666)
    val banyumas = LatLng(-7.51417, 109.29417)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(banyumas, 10f)

    }
    val listDestination by mapsViewModel.destinationForMaps.collectAsState()
    var cardIsActive by rememberSaveable { mutableStateOf(false) }
    var cardSelected: Destination? by rememberSaveable {
        mutableStateOf(null)
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
                 Spacer(modifier = Modifier.height( if (cardIsActive) 350.dp else 110.dp))
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
             var iconSelected by rememberSaveable { mutableIntStateOf(-1) }


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
                 onMyLocationButtonClick = { true },

             ) {


                 for ( index in 0 until listDestination.size) {
                     val destination = listDestination[index]
                     if (destination != null) {
                         val latLng: LatLng = LatLng(destination.latitude, destination.longitude)
                         val markerState  = remember(destination.id) { MarkerState(position = latLng) }

                         val mountainIcon = vectorToBitmap(
                             context,
                             BitmapParameters(
                                 id = R.drawable.mountain_icon_vector,
                                 iconColor = MaterialTheme.colorScheme.onPrimary.toArgb(),
                                 backgroundColor = MaterialTheme.colorScheme.primaryContainer.toArgb(),
                             )
                         )

                         MarkerInfoWindowContent(
                             state = markerState,
                             title = destination.title,
                             snippet = destination.location,
                             icon = if (iconSelected == index) {
                                 null
                             } else mountainIcon,
                             anchor = Offset(0.5f, 0.5f),

                             onInfoWindowClick = {
                                 navigateToDetail(destination)
                                 iconSelected = -1
                                                 },

                             onInfoWindowClose = {
                                 iconSelected = -1
                             },

                             onClick = {
                                 cardIsActive = true
                                 cardSelected = destination
                                 iconSelected = index

                                 false
                             }

                         ) {


                             Column(
                                 modifier = Modifier
                                     .width(200.dp)
                                     .border(
                                         2.dp,
                                         shape = MaterialTheme.shapes.medium,
                                         color = MaterialTheme.colorScheme.primaryContainer
                                     )
                                     .padding(15.dp)
                             ) {
                                 Text(
                                     text = destination.title,
                                     fontSize = 12.sp,
                                     fontWeight = FontWeight.Bold,
                                     color = Color.Black
                                 )

                                 Spacer(modifier = Modifier.height(15.dp))

                                 Text(
                                     text = destination.location,
                                     fontSize = 10.sp,
                                     color = Color.DarkGray,
                                     maxLines = 2,
                                     overflow = TextOverflow.Ellipsis

                                 )
                             }
                         }



                     }
                 }


                 userLocation?.let {
                     cameraPositionState.position = CameraPosition.fromLatLngZoom(it, 10f)
                 }

             }

             DisposableEffect(key1 = Unit) {
                 onDispose {
                     iconSelected = -1
                     cardIsActive = false
                     cardSelected = null
                 }
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

             if (cardIsActive) {
                 Box(
                     modifier = Modifier
                         .fillMaxWidth()
                         .constrainAs(listSection) {
                             start.linkTo(parent.start)
                             bottom.linkTo(parent.bottom)
                             width = Dimension.fillToConstraints
                         }
                         .padding(bottom = 130.dp, top = 15.dp, start = 30.dp, end = 30.dp)


                 ) {

                     cardSelected?.let {
                         DestinationCardLandscape(
                             destination = it,
                             modifier = Modifier.fillMaxWidth(),
                             onClick = navigateToDetail,
                             buttonVisibility = true,
                         )

                     }

                 }
             }




         }


    }
}








