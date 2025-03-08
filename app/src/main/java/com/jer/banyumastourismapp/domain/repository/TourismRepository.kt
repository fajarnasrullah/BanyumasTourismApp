package com.jer.banyumastourismapp.domain.repository

import androidx.paging.PagingData
import com.jer.banyumastourismapp.domain.model.Destination
import kotlinx.coroutines.flow.Flow

interface TourismRepository {

    fun getDestinations(): Flow<PagingData<Destination>>
}