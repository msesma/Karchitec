package com.paradigmadigital.karchitect.ui.main

import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel

interface MainActivityUserInterface {

    fun initialize(delegate: Delegate, viewModel: MainViewModel)

    interface Delegate {

        fun onRefresh()

        fun onClick(channel: ChannelUiModel)

        fun onAddChannel(channelUrl: String)
    }
}
