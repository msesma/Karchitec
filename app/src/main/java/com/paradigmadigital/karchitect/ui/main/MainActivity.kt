package com.paradigmadigital.karchitect.ui.main

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.LifecycleRegistryOwner
import android.arch.lifecycle.ViewModelProviders
import com.paradigmadigital.karchitect.platform.BaseActivity




class MainActivity : BaseActivity(), LifecycleRegistryOwner {

    private val lifecycleRegistry = LifecycleRegistry(this)

    @javax.inject.Inject
    lateinit var decorator: MainActivityDecorator
    @javax.inject.Inject
    lateinit var presenter: MainActivityPresenter

    lateinit var viewModel: ChannelsViewModel;

    override fun onCreate(bundle: android.os.Bundle?) {
        super.onCreate(bundle)
        setContentView(com.paradigmadigital.karchitect.R.layout.activity_main)
        activityComponent.inject(this)

        viewModel = ViewModelProviders.of(this).get(ChannelsViewModel::class.java)

        decorator.bind(getRootView())
        presenter.initialize(decorator, viewModel)
    }

    override fun onDestroy() {
        presenter.dispose()
        decorator.dispose()
    }

    override fun getLifecycle(): LifecycleRegistry {
        return lifecycleRegistry
    }
}
