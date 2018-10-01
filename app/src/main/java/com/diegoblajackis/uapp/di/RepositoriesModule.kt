package com.diegoblajackis.uapp.di

import android.content.Context
import com.diegoblajackis.uapp.data.net.ApiService
import com.diegoblajackis.uapp.data.repositories.UserRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoriesModule {
    @Provides
    @Singleton
    fun providesUserRepository(appContext: Context, apiService: ApiService): UserRepository {
        return UserRepository(appContext, apiService)
    }
}