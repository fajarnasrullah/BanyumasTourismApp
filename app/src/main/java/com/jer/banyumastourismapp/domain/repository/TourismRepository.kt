package com.jer.banyumastourismapp.domain.repository

import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseUser
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.model.User
import kotlinx.coroutines.flow.Flow

interface TourismRepository {

    fun getDestinations(): Flow<PagingData<Destination>>
    suspend fun getDestination(id: Int): Destination?
    suspend fun insertDestination(destination: Destination)
    suspend fun deleteDestination(destination: Destination)
    fun selectDestinations(): Flow<List<Destination>>
    suspend fun getDestinationsForMaps(): List<Destination>

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?>
    fun getCurrentUser(): FirebaseUser?
    fun saveUserData(user: FirebaseUser?, provider: String)
    fun updateUserData(name: String, desc: String)


    suspend fun insertItinerary(itinerary: Itinerary)
    suspend fun deleteItinerary(itinerary: Itinerary)
    suspend fun getItinerary(uid: String): Itinerary?

    suspend fun insertUser(user: User)
    suspend fun deleteUser(user: User)
    suspend fun getUser(uid: String): User?

}