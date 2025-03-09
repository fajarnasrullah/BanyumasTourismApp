package com.jer.banyumastourismapp.presentation.detaildestination

import com.jer.banyumastourismapp.domain.model.Destination

sealed class DetailEvent {

    data class InsertDeleteDestination(val destination: Destination): DetailEvent()

    object RemoveSideEffect: DetailEvent()

}