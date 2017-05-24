package com.paradigmadigital.karchitect.domain.cache

import android.util.Log
import com.paradigmadigital.karchitect.domain.Astronomy
import com.paradigmadigital.karchitect.domain.City
import com.paradigmadigital.karchitect.domain.CurrentWeather
import com.paradigmadigital.karchitect.domain.ForecastItem
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CacheProvider
@Inject constructor() {

    val TAG = CacheProvider::class.simpleName

    val time get() = System.currentTimeMillis()

    var cityTimestamp = 0L
    var city: City? = null
        get() {
            val timedOut = (cityTimestamp < time - TimeUnit.MINUTES.toMillis(5))
            Log.d(TAG, "GET city timed out $timedOut, previous value = $field")
            return if (!timedOut) field else null
        }
        set(value) {
            Log.d(TAG, "SET city $value, previous value = $field")
            if (value != null && !value.city.equals(field?.city)) {
                astronomy = null
                currentWeather = null
                forecast = null
            }
            cityTimestamp = time
            field = value
        }

    var astronomyTimestamp = 0L
    var astronomy: Astronomy? = null
        get() {
            val timedOut = (astronomyTimestamp < time - TimeUnit.HOURS.toMillis(1))
            Log.d(TAG, "GET astronomy timed out $timedOut, previous value = $field")
            return if (!timedOut) field else null
        }
        set(value) {
            Log.d(TAG, "SET astronomy $value, previous value = $field")
            astronomyTimestamp = time
            field = value
        }

    var currentWeatherTimestamp = 0L
    var currentWeather: CurrentWeather? = null
        get() {
            val timedOut = (currentWeatherTimestamp < time - TimeUnit.HOURS.toMillis(1))
            Log.d(TAG, "GET currentWeather timed out $timedOut, previous value = $field")
            return if (!timedOut) field else null
        }
        set(value) {
            Log.d(TAG, "SET currentWeather $value, previous value = $field")
            currentWeatherTimestamp = time
            field = value
        }

    var forecastTimestamp = 0L
    var forecast: List<ForecastItem>? = null
        get() {
            val timedOut = (forecastTimestamp < time - TimeUnit.HOURS.toMillis(1))
            Log.d(TAG, "GET forecast timed out $timedOut, previous value = $field")
            return if (!timedOut) field else null
        }
        set(value) {
            Log.d(TAG, "SET forecast $value, previous value = $field")
            forecastTimestamp = time
            field = value
        }
}


