package com.ecoheat.di

import com.ecoheat.data.remote.ApiService
import com.ecoheat.data.remote.NetworkClient
import com.ecoheat.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object  AppModule {
    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        return NetworkClient.create()
    }

    @Provides
    @Singleton
    fun provideUserRepository(apiService: ApiService): UserRepository {
        return UserRepository(apiService)
    }

}