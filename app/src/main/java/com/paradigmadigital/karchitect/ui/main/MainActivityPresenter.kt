package com.paradigmadigital.karchitect.ui.main

import com.paradigmadigital.karchitect.domain.entities.Channel
import javax.inject.Inject

class MainActivityPresenter
@Inject
constructor() {

    val TAG = MainActivityPresenter::class.simpleName

    private var decorator: MainActivityUserInterface? = null
    private lateinit var viewModel: ChannelsViewModel

    private val delegate = object : MainActivityUserInterface.Delegate {
        override fun onRefresh() {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onClick(channel: Channel) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    fun initialize(decorator: MainActivityUserInterface, viewModel: ChannelsViewModel) {
        this.decorator = decorator
        this.viewModel = viewModel
        this.decorator?.initialize(delegate, viewModel)
    }

//    fun onResume() =

    fun dispose() {
        this.decorator = null
    }
}
