package com.jer.banyumastourismapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.data.local.DaoDestination
import com.jer.banyumastourismapp.data.remote.TourismPagingSource
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.repository.TourismRepository
import kotlinx.coroutines.flow.Flow

class TourismRepositoryImpl(
    private val db: FirebaseDatabase,
    private val daoDestination: DaoDestination
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

}