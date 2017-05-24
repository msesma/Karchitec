package com.paradigmadigital.karchitect.api

import android.content.Context
import android.util.Log
import com.paradigmadigital.karchitect.BuildConfig
import com.squareup.picasso.Cache
import com.squareup.picasso.LruCache
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
class ApiModule() {

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d("Retrofit", message) })
        interceptor.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        return interceptor
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor)= OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build()

    @Provides
    @Singleton
    fun provideCache(application: Context): Cache {
        return LruCache(application)
    }

    @Provides
    @Singleton
    fun providePicasso(cache: Cache, application: Context): Picasso {
        val builder = Picasso.Builder(application)
        builder.memoryCache(cache)
        return builder.build()
    }
}
