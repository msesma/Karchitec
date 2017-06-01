package com.paradigmadigital.karchitect.ui.main

import com.paradigmadigital.karchitect.domain.FeedRepository
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.platform.isNullOrEmpty
import javax.inject.Inject

class MainActivityPresenter
@Inject
constructor(
    val repository: FeedRepository
) {

    private var decorator: MainActivityUserInterface? = null
    private lateinit var viewModel: ChannelsViewModel

    private val delegate = object : MainActivityUserInterface.Delegate {
        override fun onFab() {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

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
        initApp(viewModel)
    }

    private fun initApp(viewModel: ChannelsViewModel) {
        if (viewModel.channels.value.isNullOrEmpty()) {
            repository.getItems("http://www.paradigmatecnologico.com/feed/")
        }
    }

    fun dispose() {
        this.decorator = null
    }
}
