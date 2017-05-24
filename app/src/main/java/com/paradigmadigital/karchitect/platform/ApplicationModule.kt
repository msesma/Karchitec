package com.paradigmadigital.karchitect.platform

import android.content.Context
import android.preference.PreferenceManager
import com.paradigmadigital.karchitect.domain.cache.CacheProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: AndroidApplication) {

    @Provides
    @Singleton
    internal fun provideContext(): Context {
        return this.application
    }

    @Provides
    @Singleton
    internal fun provideSharedPreferences() = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @Singleton
    internal fun provideCacheProvider() = CacheProvider()
}
