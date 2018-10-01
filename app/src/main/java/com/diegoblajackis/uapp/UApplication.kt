package com.diegoblajackis.uapp

import android.app.Application
import com.diegoblajackis.uapp.di.*

class UApplication: Application() {
    companion object {
        private lateinit var instance: UApplication
        lateinit var dagger: ApplicationComponent

        fun getInstance() = instance
        fun getContext() = instance.applicationContext!!
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        dagger = DaggerApplicationComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .apiServiceModule(ApiServiceModule())
                .repositoriesModule(RepositoriesModule())
                .build()
    }
}