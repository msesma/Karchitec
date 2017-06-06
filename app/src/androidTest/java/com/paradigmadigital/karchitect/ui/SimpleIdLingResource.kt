package com.paradigmadigital.karchitect.ui

import android.support.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicBoolean


class SimpleIdlingResource : IdlingResource {

    @Volatile private var resourceCallback: IdlingResource.ResourceCallback? = null

    private val isIdleNow = AtomicBoolean(true)

    fun setIdleNow(idleNow: Boolean) {
        isIdleNow.set(idleNow)
        if (idleNow) resourceCallback?.onTransitionToIdle()
    }

    override fun getName(): String {
        return "Simple idling resource"
    }

    override fun isIdleNow(): Boolean {
        return isIdleNow.get()
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        resourceCallback = callback
    }
}