package com.jer.banyumastourismapp.domain.usecase.tourism

class TourismUseCase(
    val getDestinations: GetDestinations,
    val getDestination: GetDestination,
    val getDestinationsForMaps: GetDestinationsForMaps,
    val signinWithGoogle: SigninWithGoogle,
    val getCurrentUser: GetCurrentUser,
    val updateUserData: UpdateUserData,
    val insertItinerary: InsertItinerary,
    val insertPlanCard: InsertPlanCard,
    val insertPlan: InsertPlan,
    val deletePlanCard: DeletePlanCard,
    val deleteListPlan: DeleteListPlan,
    val deleteItinerary: DeleteItinerary,
    val getItinerary: GetItinerary,
    val getItineraryWithPlanCards: GetItineraryWithPlanCards,
    val insertUser: InsertUser,
    val deleteUser: DeleteUser,
    val getUser: GetUser
) {
}