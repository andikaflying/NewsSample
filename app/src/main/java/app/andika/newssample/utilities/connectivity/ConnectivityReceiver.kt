package app.andika.newssample.utilities.connectivity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import app.andika.newssample.core.NewsApplication

class ConnectivityReceiver : BroadcastReceiver() {
    //To check internet connection automatically. This method detects Receiver tag in Manifest
    override fun onReceive(context: Context, arg1: Intent) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnected

        if (connectivityReceiverListener != null) {
            connectivityReceiverListener!!.onNetworkConnectionChanged(isConnected)
        }
    }

    //To provide network connection events
    interface ConnectivityReceiverListener {
        fun onNetworkConnectionChanged(isConnected: Boolean)
    }

    //To provide checking internet connection in single variable
    companion object {
        var connectivityReceiverListener: ConnectivityReceiverListener? = null

        val isConnected: Boolean
            get() {
                val connectivityManager = NewsApplication.getContext()?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetwork = connectivityManager.activeNetworkInfo

                return activeNetwork != null && activeNetwork.isConnected
            }
    }
}