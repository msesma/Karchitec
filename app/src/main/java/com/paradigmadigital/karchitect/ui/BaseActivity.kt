package com.paradigmadigital.karchitect.ui

import android.app.Activity
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.paradigmadigital.karchitect.injection.ActivityComponent
import com.paradigmadigital.karchitect.injection.ApplicationComponent
import com.paradigmadigital.karchitect.injection.DaggerActivityComponent
import com.paradigmadigital.karchitect.platform.ActivityModule
import com.paradigmadigital.karchitect.platform.AndroidApplication

open class BaseActivity : AppCompatActivity(), LifecycleRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    private val applicationComponent: ApplicationComponent
        get() = (application as AndroidApplication).applicationComponent

    lateinit var activityComponent: ActivityComponent

    fun Activity.getRootView(): ViewGroup = (this.findViewById(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}
