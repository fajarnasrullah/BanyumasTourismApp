package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class GetUser(private val repository: TourismRepository) {

    suspend operator fun invoke(uid: String): User? {
        return repository.getUser(uid)
    }
}