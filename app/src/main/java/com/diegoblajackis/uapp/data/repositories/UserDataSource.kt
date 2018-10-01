package com.diegoblajackis.uapp.data.repositories

import android.arch.lifecycle.MediatorLiveData
import android.arch.paging.ItemKeyedDataSource
import android.content.Context
import com.diegoblajackis.uapp.R
import com.diegoblajackis.uapp.data.net.ApiService
import com.diegoblajackis.uapp.data.net.Resource
import com.diegoblajackis.uapp.data.net.model.UserListApiModel
import com.diegoblajackis.uapp.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserDataSource(
        private val appContext: Context,
        private val apiService: ApiService,
        private val resourcePagedListLiveData: MediatorLiveData<Resource<Any>>)
    : ItemKeyedDataSource<Int, User>() {

    private var page = 0
    private val seed = "uapp"

    override fun getKey(item: User) = page

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<User>) {
        val request = apiService.getUsers(page, params.requestedLoadSize, seed)
        resourcePagedListLiveData.postValue(Resource.loading())
        try {
            val response = request.execute()
            processResponse(response, callback)
        } catch (ex: Exception) {
            fireErrorResponse(ex.message)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<User>) {
        resourcePagedListLiveData.postValue(Resource.loading())
        apiService.getUsers(page, params.requestedLoadSize, seed).enqueue(UserPageCallback(callback))
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<User>) {
        //Do nothing
    }

    inner class UserPageCallback(private val callback: ItemKeyedDataSource.LoadCallback<User>)
        : Callback<UserListApiModel> {

        override fun onFailure(call: Call<UserListApiModel>, throwable: Throwable) {
            fireErrorResponse(throwable.message)
        }

        override fun onResponse(call: Call<UserListApiModel>, response: Response<UserListApiModel>) {
            processResponse(response, callback)
        }
    }

    private fun processResponse(response: Response<UserListApiModel>, callback: ItemKeyedDataSource.LoadCallback<User>) {
        response.body()?.run {
            if (response.isSuccessful) {
                processSuccessfulResponse(response, callback)
                return
            }
        }
        fireErrorResponse(appContext.getString(R.string.error_server))
    }

    private fun fireErrorResponse(message: String?) {
        val error = message ?: appContext.getString(R.string.error_server)
        resourcePagedListLiveData.postValue(Resource.error(error))
    }

    private fun processSuccessfulResponse(response: Response<UserListApiModel>, callback: ItemKeyedDataSource.LoadCallback<User>) {
        page++
        val users = UserMapper.map(response.body()!!)
        callback.onResult(users)
        resourcePagedListLiveData.postValue(Resource.success(Any()))

    }
}