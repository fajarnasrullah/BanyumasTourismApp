package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class DeleteItinerary(private val repository: TourismRepository) {

    suspend operator fun invoke (itinerary: Itinerary) {
        return repository.deleteItinerary(itinerary)
    }

}