package com.paradigmadigital.karchitect.wear

import android.content.Context
import android.os.Bundle
import android.util.Log
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class WearUpdater @Inject constructor(val context: Context) {

    private val TAG = WearUpdater::class.simpleName

    private var googleApiClient: GoogleApiClient? = null
    lateinit private var currentWeather: CurrentWeather
    lateinit private var astronomy: Astronomy
    lateinit private var forecast: List<ForecastItem>
    lateinit private var city: String

    private val connectionCallback = object : GoogleApiClient.ConnectionCallbacks {
        override fun onConnected(bundle: Bundle?) {
            handleOnConnected()
        }

        override fun onConnectionSuspended(i: Int) {
        }
    }

    fun update(currentWeather: CurrentWeather, astronomy: Astronomy, forecast: List<ForecastItem>, city: String) {
        this.currentWeather = currentWeather
        this.astronomy = astronomy
        this.forecast = forecast
        this.city = city

        buildGoogleApiClient()
    }

    private fun handleOnConnected() {

        val sunrise = astronomy.sunrise?.time
        val sunset = astronomy.sunset?.time
        val temps = mutableListOf<Int>()
        val rainsQpf = mutableListOf<Int>()
        val rainsPop = mutableListOf<Int>()

        if (forecast.get(0).time?.hours != Date().hours) {
            temps.add((currentWeather.temp * 10).toInt())
            rainsQpf.add(currentWeather.precip1hrMetric.toInt())
            rainsPop.add(if (currentWeather.precip1hrMetric > 0) 100 else 0)
        }

        for (forecastItem in forecast) {
            temps.add((forecastItem.temp * 10).toInt())
            rainsQpf.add(forecastItem.rainQuantity.toInt())
            rainsPop.add(forecastItem.rainProbability.toInt())
        }

        val putDataMapRequest = PutDataMapRequest.create(WearConstants.WATCH_SET_FORECAST_PATH)
        putDataMapRequest.getDataMap().putIntegerArrayList(WearConstants.KEY_TEMPS, temps as ArrayList<Int>)
        putDataMapRequest.getDataMap().putIntegerArrayList(WearConstants.KEY_RAINS_QPF, rainsQpf as ArrayList<Int>)
        putDataMapRequest.getDataMap().putIntegerArrayList(WearConstants.KEY_RAINS_POP, rainsPop as ArrayList<Int>)
        putDataMapRequest.getDataMap().putLong(WearConstants.KEY_SUNRISE, sunrise ?: 0)
        putDataMapRequest.getDataMap().putLong(WearConstants.KEY_SUNSET, sunset ?: TimeUnit.DAYS.toMillis(1))
        putDataMapRequest.getDataMap().putString(WearConstants.CITY, city)
        putDataMapRequest.getDataMap().putString(WearConstants.ICON, currentWeather.iconName)

        putDataMapRequest.getDataMap().putLong(WearConstants.LAST_UPDATE_TIME, Date().time)
        val request = putDataMapRequest.asPutDataRequest()
        Wearable.DataApi.putDataItem(googleApiClient, request)
                .setResultCallback { dataItemResult ->
                    if (!dataItemResult.getStatus().isSuccess()) {
                        Log.e(TAG, "Update Wearable forecast(): Failed to set the data, "
                                + "status: " + dataItemResult.getStatus().getStatusCode())
                    } else {
                        Log.d(TAG, "Update Wearable forecast(): Success "
                                + "status: " + dataItemResult.getStatus().getStatusCode())
                    }
                }

    }

    @Synchronized private fun buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(connectionCallback)
                    .addApi(Wearable.API)
                    .build()
        }

        googleApiClient?.connect()
    }
}
