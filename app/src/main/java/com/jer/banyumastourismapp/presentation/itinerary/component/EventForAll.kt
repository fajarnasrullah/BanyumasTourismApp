package com.jer.banyumastourismapp.presentation.itinerary.component

sealed class EventForAll {
    object Success : EventForAll()
    data class Error(val message: String) : EventForAll()
}