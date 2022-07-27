package com.example.PrGroupTest

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.PrGroupTest.databinding.ActivityMainBinding
import android.app.Application
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.onesignal.OneSignal
import com.appsflyer.AFInAppEventType; // Predefined event names
import com.appsflyer.AFInAppEventParameterName; // Predefined parameter names
import com.appsflyer.attribution.AppsFlyerRequestListener

const val ONESIGNAL_APP_ID = "6c1b9f6c-df6b-40a3-bbf7-c1dda22492bd"


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navController=Navigation.findNavController(this,R.id.nav_host_fragment)
        MAIN=this

        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE)

        // OneSignal Initialization
        OneSignal.initWithContext(this)
        OneSignal.setAppId(ONESIGNAL_APP_ID)
        AppsFlyerLib.getInstance().init("DtgwVkWQC5wX54373krjMk", null, this)

        AppsFlyerLib.getInstance().start(this, "DtgwVkWQC5wX54373krjMk", object :
            AppsFlyerRequestListener {
            override fun onSuccess() {
                Log.d("Kot", "Launch sent successfully")
            }

            override fun onError(errorCode: Int, errorDesc: String) {
                Log.d("Kot", "Launch failed to be sent:\n" +
                        "Error code: " + errorCode + "\n"
                        + "Error description: " + errorDesc)
            }
        })

    }
    object NetworkUtils : ConnectivityManager.NetworkCallback() {

        private val networkLiveData: MutableLiveData<Boolean> = MutableLiveData()

        fun getNetworkLiveData(context: Context): LiveData<Boolean> {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                connectivityManager.registerDefaultNetworkCallback(this)
            } else {
                val builder = NetworkRequest.Builder()
                connectivityManager.registerNetworkCallback(builder.build(), this)
            }

            var isConnected = false

            connectivityManager.allNetworks.forEach { network ->
                val networkCapability = connectivityManager.getNetworkCapabilities(network)

                networkCapability?.let {
                    if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)) {
                        isConnected = true
                        return@forEach
                    }
                }
            }

            networkLiveData.postValue(isConnected)

            return networkLiveData
        }

        override fun onAvailable(network: Network) {
            networkLiveData.postValue(true)
        }

        override fun onLost(network: Network) {
            networkLiveData.postValue(false)
        }
    }





}