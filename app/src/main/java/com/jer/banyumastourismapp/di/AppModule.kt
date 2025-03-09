package com.jer.banyumastourismapp.di

import android.app.Application
import androidx.room.Room
import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.core.Const.DB_NAME_ROOM
import com.jer.banyumastourismapp.data.local.DaoDestination
import com.jer.banyumastourismapp.data.local.DatabaseTourism
import com.jer.banyumastourismapp.data.local.ItsTypeConverter
import com.jer.banyumastourismapp.data.repository.TourismRepositoryImpl
import com.jer.banyumastourismapp.domain.repository.TourismRepository
import com.jer.banyumastourismapp.domain.usecase.tourism.GetDestination
import com.jer.banyumastourismapp.domain.usecase.tourism.GetDestinations
import com.jer.banyumastourismapp.domain.usecase.tourism.TourismUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {



    @Provides
    @Singleton
    fun provideDatabaseTourism(application: Application): DatabaseTourism {
        return Room.databaseBuilder(
            context = application,
            klass = DatabaseTourism::class.java,
            name = DB_NAME_ROOM
        )
            .addTypeConverter(ItsTypeConverter())
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun providedaoDestination(databaseTourism: DatabaseTourism) = databaseTourism.daoDestination

    @Provides
    @Singleton
    fun provideFirebaseRealtimeDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideTourismRepository(db: FirebaseDatabase, daoDestination: DaoDestination): TourismRepository {
        return TourismRepositoryImpl(db, daoDestination)
    }

    @Provides
    @Singleton
    fun provideTourismUsecase(repository: TourismRepository): TourismUseCase {
        return TourismUseCase(
            getDestinations = GetDestinations(repository),
            getDestination = GetDestination(repository)
        )
    }

}