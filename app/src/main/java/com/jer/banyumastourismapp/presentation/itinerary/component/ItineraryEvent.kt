package com.jer.banyumastourismapp.presentation.itinerary.component

import com.jer.banyumastourismapp.domain.model.Itinerary

sealed class ItineraryEvent {
    object Success : ItineraryEvent()
    data class Error(val message: String) : ItineraryEvent()
}