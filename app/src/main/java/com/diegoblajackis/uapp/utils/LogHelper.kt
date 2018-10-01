package com.diegoblajackis.uapp.utils

import android.util.Log

object LogHelper {
    private val tag = "Intive"

    fun debug(data: String?) {
        Log.d(tag, data ?: "null")
    }

    fun debug(from: Any, data: String?) {
        Log.d(tag, from.javaClass.simpleName + ": " + (data ?: "null"))
    }

    fun exception(ex: Exception) {
        ex.printStackTrace()
        Log.e(tag, ex.message ?: ex.toString())
    }
}