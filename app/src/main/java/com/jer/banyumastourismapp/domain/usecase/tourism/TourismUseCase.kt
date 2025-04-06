package com.jer.banyumastourismapp.domain.usecase.tourism

class TourismUseCase(
    val getDestinations: GetDestinations,
    val getDestination: GetDestination,
    val getDestinationsForMaps: GetDestinationsForMaps,
    val signinWithGoogle: SigninWithGoogle,
    val getCurrentUser: GetCurrentUser,
    val updateUserData: UpdateUserData,
    val insertItinerary: InsertItinerary,
    val deleteItinerary: DeleteItinerary,
    val getItinerary: GetItinerary,
    val insertUser: InsertUser,
    val deleteUser: DeleteUser,
    val getUser: GetUser
) {
}