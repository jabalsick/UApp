package com.diegoblajackis.uapp.data.net

import android.content.Context
import com.diegoblajackis.uapp.utils.LogHelper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import okhttp3.logging.HttpLoggingInterceptor

class RetrofitProvider<T>(val context: Context, private val service: Class<T>, private val serviceUrl: String) {

    fun provide() = provideRetrofit().create(service)!!

    private fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(serviceUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getOkHttpClient())
                .build()
    }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        LoggingInterceptor.addInterceptor(builder)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.connectTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }


    object LoggingInterceptor {

        fun addInterceptor(builder: OkHttpClient.Builder) {
                val httpLoggingInterceptor = HttpLoggingInterceptor { LogHelper.debug(this, it) }
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            builder.addInterceptor(httpLoggingInterceptor)
        }
    }
}