package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class InsertUser(private val repository: TourismRepository) {

    suspend operator fun invoke(user: User) {
        repository.insertUser(user)

    }

}