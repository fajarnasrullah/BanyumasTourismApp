package com.jer.banyumastourismapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.jer.banyumastourismapp.domain.model.Destination
import com.jer.banyumastourismapp.domain.model.Itinerary


@Database(entities =  [Destination::class, Itinerary::class ], version = 1, exportSchema = false)
@TypeConverters(ItsTypeConverter::class)
abstract class DatabaseTourism: RoomDatabase() {

    abstract val daoDestination: DaoDestination
    abstract val daoItinerary: DaoItinerary
}