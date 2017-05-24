package com.paradigmadigital.karchitect.scheduler

import android.content.Context
import android.util.Log
import com.firebase.jobdispatcher.*
import com.paradigmadigital.karchitect.log.DiskLogger
import javax.inject.Inject


class Scheduler
@Inject
constructor(
        context: Context,
        val forecastRetriever: ForecastRetriever,
        val diskLogger: DiskLogger
) {
    private val TAG = Scheduler::class.simpleName!!

    private val SECONDS_MIN = 1800
    private val SECONDS_MAX = 3600
    private val JOB_TAG = "forecast-job-tag"

    private var dispatcher = FirebaseJobDispatcher(GooglePlayDriver(context))
    private val job: Job

    init {
        job = dispatcher.newJobBuilder()
                .setService(ForecastJobService::class.java)
                .setTag(JOB_TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(SECONDS_MIN, SECONDS_MAX))
                .setReplaceCurrent(true)
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                .build();
    }

    fun dispatch() {
        Log.d(TAG, "Job dispatched")
        diskLogger.log(TAG, "Job dispatched")
        dispatcher.cancelAll()
        dispatcher.mustSchedule(job)
        forecastRetriever.start(null, null)
    }
}