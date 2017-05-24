package com.paradigmadigital.karchitect.ui.detail

import com.paradigmadigital.karchitect.domain.ForecastItem
import com.paradigmadigital.karchitect.ui.master.MainActivityPresenter
import javax.inject.Inject

class DetailActivityPresenter
@Inject
constructor() {

    val TAG = MainActivityPresenter::class.simpleName

    private var decorator: DetailActivityUserInterface? = null


    fun initialize(decorator: DetailActivityUserInterface, forecastItem: ForecastItem?) {
        this.decorator = decorator
        this.decorator?.initialize(forecastItem)
    }

    fun dispose() {
        this.decorator = null
    }
}
