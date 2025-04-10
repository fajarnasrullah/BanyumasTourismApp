package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.PlanCardData
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class InsertPlanCard(private val repository: TourismRepository) {

    suspend operator fun invoke(planCardData: PlanCardData): Long {
        return repository.insertPlanCard(planCardData)
    }

}