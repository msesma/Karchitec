package com.paradigmadigital.karchitect.location

import android.content.Context
import android.os.Bundle
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.jakewharton.rxrelay2.PublishRelay
import com.paradigmadigital.karchitect.usecases.GeoLookUpApiUseCase
import io.reactivex.Observable
import javax.inject.Inject


class RxLocationProvider
@Inject
constructor(val context: Context, val useCase: GeoLookUpApiUseCase) {
//  https://developers.google.com/android/guides/api-client

    private var googleApiClient: GoogleApiClient? = null

    private val relay: PublishRelay<GeoLookUp> = PublishRelay.create()

    private val connectionCallback = object : GoogleApiClient.ConnectionCallbacks {
        override fun onConnected(bundle: Bundle?) {
            val lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient)
            if (lastLocation != null) {
                useCase.execute(lastLocation.latitude.toString(), lastLocation.longitude.toString())
                        .subscribe({ handleOnResult(it) }, { googleApiClient?.disconnect() })
            }
        }

        override fun onConnectionSuspended(i: Int) {
        }
    }

    fun getGeoLookUpObservable(): Observable<GeoLookUp> {
        buildGoogleApiClient()
        return relay
    }

    @Synchronized private fun buildGoogleApiClient() {
        if (googleApiClient == null) {
            googleApiClient = GoogleApiClient.Builder(context)
                    .addConnectionCallbacks(connectionCallback)
                    .addApi(LocationServices.API)
                    .build()
        }

        googleApiClient?.connect()
    }

    private fun handleOnResult(geoLookUp: GeoLookUp) {
        relay.accept(geoLookUp)
        googleApiClient?.disconnect()
    }
}
