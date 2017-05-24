package com.paradigmadigital.karchitect.scheduler


import android.util.Log
import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService
import com.paradigmadigital.karchitect.injection.ApplicationComponent
import com.paradigmadigital.karchitect.injection.DaggerServiceComponent
import com.paradigmadigital.karchitect.log.DiskLogger
import com.paradigmadigital.karchitect.platform.AndroidApplication
import com.paradigmadigital.karchitect.platform.ServiceModule
import javax.inject.Inject


class ForecastJobService : JobService() {

    val TAG = ForecastJobService::class.simpleName!!

    private val applicationComponent: ApplicationComponent
        get() = (application as AndroidApplication).applicationComponent

    @Inject
    lateinit var forecastRetriever: ForecastRetriever
    @Inject
    lateinit var diskLogger: DiskLogger

    override fun onCreate() {
        super.onCreate()
        DaggerServiceComponent.builder()
                .applicationComponent(applicationComponent)
                .serviceModule(ServiceModule(this))
                .build()
                .inject(this)

    }

    override fun onStartJob(job: JobParameters): Boolean {
        Log.d(TAG, "onStartJob")
        diskLogger.log(TAG, "onStartJob")
        forecastRetriever.start(this, job)
        return true
    }

    override fun onStopJob(job: JobParameters): Boolean {
        Log.d(TAG, "onStopJob")
        diskLogger.log(TAG, "onStopJob")
        return false
    }

}