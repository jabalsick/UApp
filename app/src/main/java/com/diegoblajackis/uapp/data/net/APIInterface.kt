package com.diegoblajackis.uapp.data.net

import com.diegoblajackis.uapp.data.net.model.UserListApiModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api")
    fun getUsers(@Query("page") page: Int,
                 @Query("results") results: Int,
                 @Query("seed") seed: String)
            : Call<UserListApiModel>

}