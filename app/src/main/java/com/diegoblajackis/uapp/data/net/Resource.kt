package com.diegoblajackis.uapp.data.net

class Resource<T> private constructor(
        val status: Status,
        val data: T? = null,
        val resourceException: Throwable? = null) {

    companion object {
        enum class Status {
            SUCCESS, ERROR, LOADING
        }

        fun <T> success(data: T): Resource<T> {
            return Resource(Status.SUCCESS, data = data)
        }

        fun <T> error(errorCode: String): Resource<T> {
            return Resource(Status.ERROR, resourceException = Throwable(errorCode))
        }

        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data = data)
        }
    }
}