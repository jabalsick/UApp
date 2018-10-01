package com.diegoblajackis.uapp.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(application: Application) {
    private val applicationContext = application.applicationContext!!

    @Provides
    @Singleton
    fun providesApplicationContext(): Context {
        return applicationContext
    }
}