package com.teaml.iq.volunteer.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Mahmood Ali on 31/01/2018.
 */
object NetworkUtils {

    fun isNetworkConnected(context: Context): Boolean {

        val connectivityManager =  context.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null &&  networkInfo.isConnectedOrConnecting
    }
}