package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.PlanCardData
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class DeletePlanCard(private val repository: TourismRepository) {
    suspend operator fun invoke(planCard: PlanCardData) {
        return repository.deletePlanCard(planCard)
    }
}