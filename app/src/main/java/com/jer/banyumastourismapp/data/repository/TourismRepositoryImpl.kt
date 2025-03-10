package com.jer.banyumastourismapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.data.local.DaoDestination
import com.jer.banyumastourismapp.data.remote.TourismPagingSource
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.repository.TourismRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class TourismRepositoryImpl(
    private val db: FirebaseDatabase,
    private val daoDestination: DaoDestination,
    private val auth: FirebaseAuth
): TourismRepository {
    override fun getDestinations(): Flow<PagingData<Destination>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                TourismPagingSource(db)
            }
        ).flow
    }

    override suspend fun getDestination(id: Int): Destination? {
        return daoDestination.getDestination(id)
    }

    override suspend fun insertDestination(destination: Destination) {
        return daoDestination.insert(destination)
    }

    override suspend fun deleteDestination(destination: Destination) {
        return daoDestination.delete(destination)
    }

    override fun selectArticles(): Flow<List<Destination>> {
        return daoDestination.getDestinations()
    }

    override suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?> {
        return try {
            val credential: AuthCredential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            Result.success(authResult.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

}