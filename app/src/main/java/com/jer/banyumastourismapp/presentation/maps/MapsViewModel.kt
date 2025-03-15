package com.jer.banyumastourismapp.presentation.maps

import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.model.LatLng
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(val useCase: TourismUseCase): ViewModel() {

    private val _userLocation = mutableStateOf<LatLng?>(null)
    val userLocation: State<LatLng?> = _userLocation

    private val _destinationForMaps = MutableStateFlow<List<Destination>>(emptyList())
    val destinationForMaps: StateFlow<List<Destination>> = _destinationForMaps.asStateFlow()

    val destinations = useCase.getDestinations().cachedIn(viewModelScope)

    fun fetchUserLocation(context: Context, fusedLocationProviderClient: FusedLocationProviderClient) {
        if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    _userLocation.value = LatLng(location.latitude, location.longitude)
                }
            }
        } else {
            Timber.e("Location permission not granted")
        }
    }


    init {
        getDestinationsForMaps()
    }
    fun getDestinationsForMaps () {
        viewModelScope.launch {
            try {
                val destinations = useCase.getDestinationsForMaps()
                _destinationForMaps.value = destinations
            } catch (e: Exception) {
                Log.e("MapsViewModel", "Error fetching destinations: ${e.message}")
            }
        }

    }




}