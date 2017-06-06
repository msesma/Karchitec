package com.paradigmadigital.karchitect.ui.main

import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import com.paradigmadigital.karchitect.navigation.Navigator
import com.paradigmadigital.karchitect.repository.FeedRepository
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

        override fun onClick(channel: ChannelUiModel) = navigator.navigateToDetail(channel)
    }

    fun initialize(decorator: MainActivityUserInterface, viewModel: MainViewModel) {
        this.decorator = decorator
        this.viewModel = viewModel
        this.decorator?.initialize(delegate, viewModel)
    }

    fun dispose() {
        this.decorator = null
    }
}
