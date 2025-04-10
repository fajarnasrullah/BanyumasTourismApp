package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.ItineraryWithPlanCards
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class GetItineraryWithPlanCards(private val repository: TourismRepository) {
    suspend operator fun invoke(uid: String): ItineraryWithPlanCards? {
        return repository.getItineraryWithPlanCards(uid)
    }
}