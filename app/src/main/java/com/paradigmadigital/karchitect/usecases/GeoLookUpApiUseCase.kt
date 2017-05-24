package com.paradigmadigital.karchitect.usecases

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


class GeoLookUpApiUseCase
@Inject
constructor(client: OkHttpClient, endpoint: Endpoint) {

    val service: WeatherService

    init {
        service = Retrofit.Builder()
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(endpoint.URL)
                .build()
                .create(WeatherService::class.java)
    }

    fun execute(latitude: String, longitude: String) = service.getGeoLookUp(latitude, longitude)
            .take(1)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
}