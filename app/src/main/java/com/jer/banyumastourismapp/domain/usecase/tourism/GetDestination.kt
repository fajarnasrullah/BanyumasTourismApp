package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class GetDestination (
    private val repository: TourismRepository
) {

    suspend operator fun invoke (id: Int): Destination? {
        return repository.getDestination(id)
    }
}