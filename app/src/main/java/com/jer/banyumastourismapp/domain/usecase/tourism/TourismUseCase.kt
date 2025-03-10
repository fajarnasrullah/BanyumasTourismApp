package com.jer.banyumastourismapp.domain.usecase.tourism

class TourismUseCase(
    val getDestinations: GetDestinations,
    val getDestination: GetDestination,
    val signinWithGoogle: SigninWithGoogle,
    val getCurrentUser: GetCurrentUser
) {
}