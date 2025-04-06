package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class GetItinerary(private val repository: TourismRepository) {
    suspend operator fun invoke(uid: String): Itinerary? {
        return repository.getItinerary(uid)
    }
}