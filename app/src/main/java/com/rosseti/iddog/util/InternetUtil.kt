package com.rosseti.iddog.util

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.lifecycle.LiveData

data class InternetUtil(val application: Application) : LiveData<InternetViewState>() {

    private var broadcastReceiver: BroadcastReceiver? = null


    fun isInternetAvalable(): Boolean {
        val cm = application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    override fun onActive() {
        registerBroadCastReceiver()
    }

    override fun onInactive() {
        unRegisterBroadCastReceiver()
    }

    private fun registerBroadCastReceiver() {
        if (broadcastReceiver == null) {
            val filter = IntentFilter()
            filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)

            broadcastReceiver = object : BroadcastReceiver() {
                override fun onReceive(context: Context, intent: Intent) {
                    val extras = intent.extras
                    val info = extras.getParcelable<NetworkInfo>("networkInfo")
                    value = if (info != null && info.isConnected) InternetViewState.HasInternet else InternetViewState.HasNoInternet
                }
            }

            application.registerReceiver(broadcastReceiver, filter)
        }
    }

    private fun unRegisterBroadCastReceiver() {
        if (broadcastReceiver != null) {
            application.unregisterReceiver(broadcastReceiver)
            broadcastReceiver = null
        }
    }
}