package com.paradigmadigital.karchitect.ui.main

import android.arch.lifecycle.ViewModelProviders
import com.paradigmadigital.karchitect.ui.BaseActivity

class MainActivity : BaseActivity() {

    @javax.inject.Inject
    lateinit var decorator: MainActivityDecorator
    @javax.inject.Inject
    lateinit var presenter: MainActivityPresenter

    lateinit var viewModel: MainViewModel

    override fun onCreate(bundle: android.os.Bundle?) {
        super.onCreate(bundle)
        setContentView(com.paradigmadigital.karchitect.R.layout.activity_main)
        activityComponent.inject(this)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        decorator.bind(getRootView())
        presenter.initialize(decorator, viewModel)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
        decorator.dispose()
    }
}
