package com.paradigmadigital.karchitect.api

import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.paradigmadigital.karchitect.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

@Module
class ApiModule() {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("Retrofit", message) })
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addNetworkInterceptor(StethoInterceptor())
            .build()
}
