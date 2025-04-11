package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.repository.TourismRepository

class DeleteListPlan(private val repository: TourismRepository) {

    suspend operator fun invoke(planCardDataId: Int) {
        return repository.deleteListPlan(planCardDataId)
    }
}