package com.paradigmadigital.karchitect.ui.detail

import android.os.Bundle
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.platform.BaseActivity
import javax.inject.Inject


class DetailActivity : BaseActivity() {

    @Inject
    lateinit var decorator: DetailActivityDecorator
    @Inject
    lateinit var presenter: DetailActivityPresenter

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_detail)
        activityComponent.inject(this)

        decorator.bind(getRootView())
        presenter.initialize(decorator, intent?.getSerializableExtra(getString(R.string.item_key)) as ForecastItem?)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}
