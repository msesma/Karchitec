package com.paradigmadigital.karchitect.ui.main

import com.paradigmadigital.karchitect.domain.FeedRepository
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.navigation.Navigator
import com.paradigmadigital.karchitect.platform.isNullOrEmpty
import javax.inject.Inject

class MainActivityPresenter
@Inject
constructor(
        val repository: FeedRepository,
        val navigator: Navigator
) {

    private var decorator: MainActivityUserInterface? = null
    private lateinit var viewModel: MainViewModel

    private val delegate = object : MainActivityUserInterface.Delegate {
        override fun onAddChannel(channelUrl: String) = repository.addChannel(channelUrl)

        override fun onRefresh() = repository.refreshItems()

        override fun onClick(channel: Channel) = navigator.navigateToDetail(channel)
    }

    fun initialize(decorator: MainActivityUserInterface, viewModel: MainViewModel) {
        this.decorator = decorator
        this.viewModel = viewModel
        this.decorator?.initialize(delegate, viewModel)
        initApp(viewModel)
    }

    private fun initApp(viewModel: MainViewModel) {
        if (viewModel.channels.value.isNullOrEmpty()) {
            repository.addChannel("http://www.paradigmatecnologico.com/feed/")
            repository.addChannel("http://feed.androidauthority.com/")
        }
    }

    fun dispose() {
        this.decorator = null
    }
}
