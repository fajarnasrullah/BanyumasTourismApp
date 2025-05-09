package com.jer.banyumastourismapp.domain.repository

import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseUser
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.model.ItineraryWithPlanCards
import com.jer.banyumastourismapp.domain.model.Story
import com.jer.banyumastourismapp.domain.model.Plan
import com.jer.banyumastourismapp.domain.model.PlanCardData
import com.jer.banyumastourismapp.domain.model.Ticket
import com.jer.banyumastourismapp.domain.model.TransactionRequest
import com.jer.banyumastourismapp.domain.model.TransactionResponse
import com.jer.banyumastourismapp.domain.model.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface TourismRepository {

    fun getDestinations(): Flow<PagingData<Destination>>
    suspend fun getDestination(id: Int): Destination?
    suspend fun getDestinationByTitle(title: String): List<Destination>
    suspend fun insertDestination(destination: Destination)
    suspend fun deleteDestination(destination: Destination)
    fun selectDestinations(): Flow<List<Destination>>
    suspend fun getDestinationsForMaps(): List<Destination>

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?>
    fun getCurrentUser(): FirebaseUser?
    fun saveUserData(user: FirebaseUser?, provider: String)
    fun updateUserData(name: String, desc: String)


    suspend fun insertItinerary(itinerary: Itinerary): Long
    suspend fun insertPlanCard(planCard: PlanCardData): Long
    suspend fun insertPlan(plan: Plan)
    suspend fun deleteItinerary(itinerary: Itinerary)
    suspend fun deletePlanCard(planCard: PlanCardData)
    suspend fun deleteListPlan(planCardDataId: Int)
    suspend fun getItinerary(uid: String): Itinerary?
    suspend fun getItineraryWithPlanCards(uid: String): ItineraryWithPlanCards?


    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun getUser(uid: String): User?

    suspend fun createTransaction(request: TransactionRequest): Response<TransactionResponse>

    suspend fun insertTicket(ticket: Ticket)
    suspend fun deleteTicket(ticket: Ticket)
    suspend fun getTicket(uid: String): Ticket
    suspend fun getTicketHistory(uid: String): List<Ticket>


    suspend fun insertStory(story: Story)
    suspend fun getStory(): List<Story>

}