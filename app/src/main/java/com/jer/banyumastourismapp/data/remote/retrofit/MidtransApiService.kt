package com.jer.banyumastourismapp.data.remote.retrofit

import com.jer.banyumastourismapp.domain.model.TransactionRequest
import com.jer.banyumastourismapp.domain.model.TransactionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MidtransApiService {
    @POST("transaction")
    suspend fun createTransaction(@Body request: TransactionRequest): Response<TransactionResponse>
}