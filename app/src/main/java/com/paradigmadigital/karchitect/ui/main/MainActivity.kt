package com.paradigmadigital.karchitect.ui.main

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import com.paradigmadigital.karchitect.platform.BaseActivity


class MainActivity : BaseActivity(), LifecycleRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    @javax.inject.Inject
    lateinit var decorator: MainActivityDecorator
    @javax.inject.Inject
    lateinit var presenter: MainActivityPresenter

    override fun onCreate(bundle: android.os.Bundle?) {
        super.onCreate(bundle)
        setContentView(com.paradigmadigital.karchitect.R.layout.activity_main)
        activityComponent.inject(this)

        decorator.bind(getRootView())
        presenter.initialize(decorator)
    }


    override fun onResume() {
        super.onResume()
//        presenter.onResume()
    }

    override fun onDestroy() {
        presenter.dispose()
        decorator.dispose()
    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}
