package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class GetDestinationsForMaps (private val repository: TourismRepository) {

    suspend operator fun invoke(): List<Destination> {
        return repository.getDestinationsForMaps()
    }
}