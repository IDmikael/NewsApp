package com.idmikael.newstestapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Inject

class Utils @Inject constructor(private val context: Context){

    fun isConnectedToInternet(): Boolean {
        val connectivity = context.getSystemService(
                Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val info = connectivity.activeNetworkInfo

        return info != null
    }

}