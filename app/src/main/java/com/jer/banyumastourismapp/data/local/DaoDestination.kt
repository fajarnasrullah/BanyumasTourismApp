package com.jer.banyumastourismapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jer.banyumastourismapp.domain.model.Destination
import kotlinx.coroutines.flow.Flow

@Dao
interface DaoDestination {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(destination: Destination)

    @Delete
    suspend fun delete(destination: Destination)

    @Query("SELECT * FROM Destination")
    fun getDestinations(): Flow<List<Destination>>

    @Query("SELECT * FROM Destination WHERE id=:id")
    suspend fun getDestination(id: Int): Destination

}