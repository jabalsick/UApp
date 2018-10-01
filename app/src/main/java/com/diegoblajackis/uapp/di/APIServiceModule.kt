package com.diegoblajackis.uapp.di

import android.content.Context
import com.diegoblajackis.uapp.data.net.ApiService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class ApiServiceModule {
    @Provides
    @Singleton
    fun providesApiService(context: Context): ApiService {
        return ApiService(context)
    }
}