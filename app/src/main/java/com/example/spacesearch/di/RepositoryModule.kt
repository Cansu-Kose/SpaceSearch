package com.example.spacesearch.di

import com.example.spacesearch.data.repository.SearchRepository
import com.example.spacesearch.data.service.remote.SearchAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.DelicateCoroutinesApi
import javax.inject.Singleton

@DelicateCoroutinesApi
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Provides RemoteDataRepository for access api service method
     */
    @Singleton
    @Provides
    fun provideSearchRepository(
        apiService: SearchAPIService,
    ): SearchRepository {
        return SearchRepository(
            apiService
        )
    }

}