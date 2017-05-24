package com.paradigmadigital.karchitect.platform

import android.app.Service
import com.paradigmadigital.karchitect.injection.PerService
import dagger.Module
import dagger.Provides

@Module
class ServiceModule(private val service: Service) {

    @Provides
    @PerService
    internal fun service(): Service = this.service
}

