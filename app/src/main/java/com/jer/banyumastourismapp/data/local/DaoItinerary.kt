package com.jer.banyumastourismapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jer.banyumastourismapp.domain.model.Itinerary

@Dao
interface DaoItinerary {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItinerary(itinerary: Itinerary)

    @Delete
    suspend fun deleteItinerary(itinerary: Itinerary)

    @Query("SELECT * FROM Itinerary WHERE uid =:uid ")
    suspend fun getItinerary(uid: String): Itinerary
}