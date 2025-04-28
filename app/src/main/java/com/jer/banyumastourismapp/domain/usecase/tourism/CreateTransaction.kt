package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.TransactionRequest
import com.jer.banyumastourismapp.domain.model.TransactionResponse
import com.jer.banyumastourismapp.domain.repository.TourismRepository
import retrofit2.Response

class CreateTransaction (private val repository: TourismRepository) {
    suspend operator fun invoke(request: TransactionRequest): Response<TransactionResponse> {
        return repository.createTransaction(request)
    }
}