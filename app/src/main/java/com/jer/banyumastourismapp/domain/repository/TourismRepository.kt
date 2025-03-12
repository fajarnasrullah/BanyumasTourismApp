package com.jer.banyumastourismapp.domain.repository

import androidx.paging.PagingData
import com.google.firebase.auth.FirebaseUser
import com.jer.banyumastourismapp.domain.model.Destination
import kotlinx.coroutines.flow.Flow

interface TourismRepository {

    fun getDestinations(): Flow<PagingData<Destination>>
    suspend fun getDestination(id: Int): Destination?
    suspend fun insertDestination(destination: Destination)
    suspend fun deleteDestination(destination: Destination)
    fun selectArticles(): Flow<List<Destination>>

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?>
    fun getCurrentUser(): FirebaseUser?
    fun saveUserData(user: FirebaseUser?, provider: String)
    fun updateUserData(name: String, desc: String)
}