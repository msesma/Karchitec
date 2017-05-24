package com.paradigmadigital.karchitect.platform

import android.app.Activity
import android.app.Application
import android.os.Bundle

open class ActivityLifecycleAdapter : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, bundle: Bundle) {}

    override fun onActivityStarted(activity: Activity) {}

    override fun onActivityResumed(activity: Activity) {}

    override fun onActivityPaused(activity: Activity) {}

    override fun onActivityStopped(activity: Activity) {}

    override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

    override fun onActivityDestroyed(activity: Activity) {}
}
