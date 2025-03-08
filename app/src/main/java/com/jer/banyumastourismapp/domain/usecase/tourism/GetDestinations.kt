package com.jer.banyumastourismapp.domain.usecase.tourism

import androidx.paging.PagingData
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.repository.TourismRepository
import kotlinx.coroutines.flow.Flow

class GetDestinations (
    private val repository: TourismRepository
) {

    operator fun invoke(): Flow<PagingData<Destination>> {
        return repository.getDestinations()
    }

}