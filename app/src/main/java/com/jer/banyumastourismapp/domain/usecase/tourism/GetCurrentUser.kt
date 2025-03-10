package com.jer.banyumastourismapp.domain.usecase.tourism

import com.google.firebase.auth.FirebaseUser
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class GetCurrentUser(private val repository: TourismRepository) {

    operator fun invoke(): FirebaseUser? {
        return repository.getCurrentUser()
    }

}