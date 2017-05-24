package com.paradigmadigital.karchitect.scheduler

import android.Manifest
import android.content.Context
import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.paradigmadigital.karchitect.domain.Astronomy
import com.paradigmadigital.karchitect.domain.City
import com.paradigmadigital.karchitect.domain.CurrentWeather
import com.paradigmadigital.karchitect.domain.ForecastItem
import com.paradigmadigital.karchitect.domain.cache.CacheProvider
import com.paradigmadigital.karchitect.log.DiskLogger
import com.paradigmadigital.karchitect.usecases.AstronomyApiUseCase
import com.paradigmadigital.karchitect.usecases.CityUseCase
import com.paradigmadigital.karchitect.usecases.ConditionsApiUseCase
import com.paradigmadigital.karchitect.usecases.ForecastApiUseCase
import com.paradigmadigital.karchitect.wear.WearUpdater
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class ForecastRetriever @Inject constructor(
        private val conditionsUseCase: ConditionsApiUseCase,
        private val astronomyUseCase: AstronomyApiUseCase,
        private val hourlyUseCase: ForecastApiUseCase,
        private val cityUseCase: CityUseCase,
        private val cache: CacheProvider,
        private val context: Context,
        private val wearUpdater: WearUpdater,
        private val diskLogger: DiskLogger
) {

    private val TAG = ForecastRetriever::class.simpleName!!
    private var jobService: JobService? = null
    private var jobParameters: JobParameters? = null

    fun start(jobService: ForecastJobService?, jobParameters: JobParameters?) {
        this.jobService = jobService
        this.jobParameters = jobParameters

        if (cache.city != null) {
            handleOnCityResult(cache.city!!)
            return
        }

        if (EasyPermissions.hasPermissions(context, Manifest.permission.ACCESS_COARSE_LOCATION)) {
            cityUseCase.execute()
                    .subscribe(
                            { cache.city = it; this.handleOnCityResult(it) },
                            { handleOnError(it) }
                    )
        }
    }

    private fun handleOnCityResult(city: City) {
        Log.d(TAG, cache.city.toString())
        handleOnResult(city = city)

        if (cache.currentWeather == null) conditionsUseCase.execute(country = city.countryCode, city = city.city)
                .subscribe(
                        { cache.currentWeather = it; handleOnResult(currentWeather = it) },
                        { handleOnError(it) }
                )

        if (cache.astronomy == null) astronomyUseCase.execute(country = city.countryCode, city = city.city)
                .subscribe(
                        { cache.astronomy = it; handleOnResult(astronomy = it) },
                        { handleOnError(it) }
                )

        if (cache.forecast == null) hourlyUseCase.execute(country = city.countryCode, city = city.city)
                .subscribe(
                        { cache.forecast = it; handleOnResult(forecast = it) },
                        { handleOnError(it) }
                )
    }

    private fun handleOnError(throwable: Throwable?) {
        Log.d(TAG, "Job finished FAIL: " + throwable.toString())
        diskLogger.log(TAG, "Job finished FAIL: " + throwable.toString())
        jobService?.jobFinished(jobParameters!!, true)
    }

    private fun handleOnResult(
            currentWeather: CurrentWeather? = cache.currentWeather,
            astronomy: Astronomy? = cache.astronomy,
            forecast: List<ForecastItem>? = cache.forecast,
            city: City? = cache.city) {
        if (currentWeather == null || astronomy == null || forecast == null) return
        wearUpdater.update(currentWeather, astronomy, forecast, city?.city ?: "")
        diskLogger.log(TAG, "Job finished OK in: " + city?.city)
        Log.d(TAG, "Job finished OK")
        jobService?.jobFinished(jobParameters!!, false)
    }

}

