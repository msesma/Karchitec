package com.paradigmadigital.karchitect.platform

import android.app.Application
import com.facebook.stetho.Stetho
import com.paradigmadigital.karchitect.injection.ApplicationComponent
import com.paradigmadigital.karchitect.injection.DaggerApplicationComponent

class AndroidApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)

        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }
}
