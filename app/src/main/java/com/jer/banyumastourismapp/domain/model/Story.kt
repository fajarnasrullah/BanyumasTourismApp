package com.jer.banyumastourismapp.domain.model


data class Story(
    val name: String? = null,
    val photoUrl: String? = null,
    val message: String? = null,
    val destination: String? = null,
    val category: String? = null,
    val rating: Double? = 0.0,
    val time: Long? = 0
)
