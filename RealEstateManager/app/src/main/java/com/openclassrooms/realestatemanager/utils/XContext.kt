package com.openclassrooms.realestatemanager.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo

val Context.hasInternet: Boolean
    get() {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }
