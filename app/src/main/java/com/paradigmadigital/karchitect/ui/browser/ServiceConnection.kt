package com.paradigmadigital.karchitect.ui.browser

import android.content.ComponentName
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsServiceConnection

import java.lang.ref.WeakReference

class ServiceConnection(connectionCallback: ServiceConnection.ServiceConnectionCallback) : CustomTabsServiceConnection() {
    private val callbackWeakReference: WeakReference<ServiceConnectionCallback>

    init {
        callbackWeakReference = WeakReference(connectionCallback)
    }

    override fun onCustomTabsServiceConnected(name: ComponentName, client: CustomTabsClient) {
        val connectionCallback = callbackWeakReference.get()
        connectionCallback?.onServiceConnected(client)
    }

    override fun onServiceDisconnected(name: ComponentName) {
        val connectionCallback = callbackWeakReference.get()
        connectionCallback?.onServiceDisconnected()
    }

    interface ServiceConnectionCallback {

        fun onServiceConnected(client: CustomTabsClient)

        fun onServiceDisconnected()
    }
}