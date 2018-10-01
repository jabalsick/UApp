package com.diegoblajackis.uapp.data.repositories

import com.diegoblajackis.uapp.data.net.model.UserApiModel
import com.diegoblajackis.uapp.data.net.model.UserListApiModel
import com.diegoblajackis.uapp.model.User


object UserMapper {
    fun map(userBackend: UserApiModel): User {
        val user = User()
        user.firstname = userBackend.name.first
        user.lastname = userBackend.name.last
        user.email = userBackend.email
        user.thumbnail = userBackend.picture.thumbnail
        user.picture = userBackend.picture.large
        user.age = userBackend.dob.age
        user.city = userBackend.location.city
        user.state = userBackend.location.state
        user.street = userBackend.location.street

        return user
    }

    fun map(userListBackend: UserListApiModel): ArrayList<User> {
        val users = ArrayList<User>()
        userListBackend.results?.forEach { users.add(map(it)) }
        return users
    }
}