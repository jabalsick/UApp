package com.diegoblajackis.uapp.ui.viewModels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.support.annotation.NonNull
import com.diegoblajackis.uapp.UApplication
import com.diegoblajackis.uapp.data.repositories.UserRepository
import javax.inject.Inject

class UsersViewModel(@NonNull application: Application) : AndroidViewModel(application) {

    private val pageSize = 20

    @Inject
    lateinit var userRepository: UserRepository

    init {
        UApplication.dagger.inject(this)
    }

    fun getUsers() = userRepository.getPagedData(pageSize)
}