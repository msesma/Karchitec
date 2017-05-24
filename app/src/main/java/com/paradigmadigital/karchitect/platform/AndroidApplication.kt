package com.paradigmadigital.karchitect.platform

import android.app.Application
import com.paradigmadigital.karchitect.injection.ApplicationComponent
import com.paradigmadigital.karchitect.injection.DaggerApplicationComponent

class AndroidApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
