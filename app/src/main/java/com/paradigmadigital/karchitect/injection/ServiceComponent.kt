package com.paradigmadigital.karchitect.injection

import android.app.Service
import com.paradigmadigital.karchitect.platform.ServiceModule
import com.paradigmadigital.karchitect.scheduler.ForecastJobService
import dagger.Component

@PerService
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ServiceModule::class))
interface ServiceComponent {

    fun inject(forecastJobService: ForecastJobService)

    //Exposed to sub-graphs.
    fun service(): Service
}
