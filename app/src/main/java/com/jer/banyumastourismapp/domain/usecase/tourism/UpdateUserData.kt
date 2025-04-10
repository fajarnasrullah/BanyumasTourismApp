package com.jer.banyumastourismapp.domain.usecase.tourism

import com.jer.banyumastourismapp.domain.repository.TourismRepository

class UpdateUserData(private val repository: TourismRepository) {

    operator fun invoke(name: String, desc: String) {
        return repository.updateUserData(name, desc)
    }
}