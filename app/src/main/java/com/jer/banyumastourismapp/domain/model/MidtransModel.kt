package com.jer.banyumastourismapp.domain.model

data class TransactionRequest(
    val orderId: String,
    val grossAmount: Int,
    val customerName: String,
    val customerEmail: String

)

data class TransactionResponse(
    val message: String,
    val token: String,
    val redirect_url: String
)