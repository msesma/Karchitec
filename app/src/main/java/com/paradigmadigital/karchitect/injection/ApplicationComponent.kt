package com.paradigmadigital.karchitect.injection

import android.content.Context
import android.content.SharedPreferences
import com.paradigmadigital.karchitect.api.ApiModule
import com.paradigmadigital.karchitect.domain.cache.CacheProvider
import com.paradigmadigital.karchitect.platform.ApplicationModule
import com.squareup.picasso.Picasso
import dagger.Component
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        ApplicationModule::class,
        ApiModule::class))
interface ApplicationComponent {

    //Exposed to sub-graphs
    fun provideContext(): Context

    fun provideSharedPreferences(): SharedPreferences

    fun provideOkHttpClient(): OkHttpClient

    fun providePicasso(): Picasso

    fun provideCacheProvider(): CacheProvider
}
