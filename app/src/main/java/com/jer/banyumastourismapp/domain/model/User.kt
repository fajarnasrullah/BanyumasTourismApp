package com.jer.banyumastourismapp.domain.model

data class User(
    val uid: String = "",
    val name: String? = null,
    val email: String? = null,
    val desc: String? = null,
    val provider: String = "",
    )
