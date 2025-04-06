package com.jer.banyumastourismapp.domain.usecase.tourism

import com.google.firebase.auth.FirebaseUser
import com.jer.banyumastourismapp.domain.repository.TourismRepository

class SigninWithGoogle (private val repository: TourismRepository) {

    suspend operator fun invoke(idToken: String): Result<FirebaseUser?> {
        return repository.signInWithGoogle(idToken)
    }

}