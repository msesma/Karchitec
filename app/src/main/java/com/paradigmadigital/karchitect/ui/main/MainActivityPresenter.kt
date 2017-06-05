package com.paradigmadigital.karchitect.ui.main

import com.paradigmadigital.karchitect.repository.FeedRepository
import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import com.paradigmadigital.karchitect.navigation.Navigator
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

        override fun onRefresh() {
            repository.refreshItems()
            decorator?.stopRefresh() //TODO stop cos if nothing changed decorator doesn't know refresh is done. Change this when error control is implemented
        }

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
