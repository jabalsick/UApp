package com.diegoblajackis.uapp.data.repositories

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import android.content.Context
import android.support.annotation.MainThread
import com.diegoblajackis.uapp.data.net.ApiService
import com.diegoblajackis.uapp.data.net.Resource
import com.diegoblajackis.uapp.model.User

class UserRepository(private val appContext: Context, private val apiService: ApiService) {

    @MainThread
    fun getPagedData(pageSize: Int): LiveData<Resource<Any>> {
        val resourcePagedListLiveData = MediatorLiveData<Resource<Any>>()
        val dataSourceFactory = DataSourceFactory(resourcePagedListLiveData)
        val pagedListConfig = PagedList.Config.Builder().setInitialLoadSizeHint(pageSize).setPageSize(pageSize).build()
        val pagedListLiveData = LivePagedListBuilder(dataSourceFactory, pagedListConfig).build()
        resourcePagedListLiveData.addSource(pagedListLiveData) { resourcePagedListLiveData.value = Resource.loading(it) }
        return resourcePagedListLiveData
    }

    inner class DataSourceFactory(private val resourcePagedListLiveData: MediatorLiveData<Resource<Any>>)
        : DataSource.Factory<Int, User>() {

        override fun create() = UserDataSource(appContext, apiService, resourcePagedListLiveData)
    }

}