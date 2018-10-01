package com.diegoblajackis.uapp.data.net

import android.content.Context

class ApiService(val context: Context) {
    private var api: ApiInterface
    private val serviceUrl = "https://randomuser.me/"

    init {
        api = RetrofitProvider(context, ApiInterface::class.java, serviceUrl).provide()
    }

    fun getUsers(page: Int, results: Int, seed: String) = api.getUsers(page, results, seed)

}