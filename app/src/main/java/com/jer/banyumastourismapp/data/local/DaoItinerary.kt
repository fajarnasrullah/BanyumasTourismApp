package com.jer.banyumastourismapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.jer.banyumastourismapp.domain.model.Itinerary
import com.jer.banyumastourismapp.domain.model.ItineraryWithPlanCards
import com.jer.banyumastourismapp.domain.model.Plan
import com.jer.banyumastourismapp.domain.model.PlanCardData

@Dao
interface DaoItinerary {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItinerary(itinerary: Itinerary): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlanCard(planCard: PlanCardData): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPlan(plan: Plan)

    @Delete
    suspend fun deleteItinerary(itinerary: Itinerary)

    @Delete
    suspend fun deletePlanCard(planCard: PlanCardData)

    @Query("DELETE FROM `Plan` WHERE planCardDataId =:planCardDataId")
    suspend fun deleteListPlan(planCardDataId: Int)

    @Query("SELECT * FROM Itinerary WHERE uid =:uid ")
    suspend fun getItinerary(uid: String): Itinerary

    @Transaction
    @Query("SELECT * FROM Itinerary WHERE uid =:uid")
    suspend fun getItineraryWithPlanCards(uid: String): ItineraryWithPlanCards
}