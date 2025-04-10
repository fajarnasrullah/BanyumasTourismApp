package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.Plan
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class InsertPlan(private val repository: TourismRepository) {
    suspend operator fun invoke(plan: Plan) {
        return repository.insertPlan(plan)
    }
}