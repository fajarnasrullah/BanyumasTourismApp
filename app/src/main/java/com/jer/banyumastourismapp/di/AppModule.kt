package com.jer.banyumastourismapp.di

import com.google.firebase.database.FirebaseDatabase
import com.jer.banyumastourismapp.data.repository.TourismRepositoryImpl
import com.jer.banyumastourismapp.domain.repository.TourismRepository
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
    fun provideFirebaseRealtimeDatabase(): FirebaseDatabase {
        return FirebaseDatabase.getInstance()
    }

    @Provides
    @Singleton
    fun provideTourismRepository(db: FirebaseDatabase): TourismRepository {
        return TourismRepositoryImpl(db)
    }

    @Provides
    @Singleton
    fun provideTourismUsecase(repository: TourismRepository): TourismUseCase {
        return TourismUseCase(
            getDestinations = GetDestinations(repository)
        )
    }

}