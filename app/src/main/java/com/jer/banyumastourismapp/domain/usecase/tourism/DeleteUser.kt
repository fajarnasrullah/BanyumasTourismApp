package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.model.User
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class DeleteUser(private val repository: TourismRepository) {
    suspend operator fun invoke(user: User) {
        return repository.deleteUser(user)
    }
}