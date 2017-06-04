package com.paradigmadigital.karchitect.ui.main

import com.paradigmadigital.karchitect.domain.entities.Channel

interface MainActivityUserInterface {

    fun initialize(delegate: Delegate, viewModel: MainViewModel)

    fun stopRefresh()

    interface Delegate {

        fun onRefresh()

        fun onClick(channel: Channel)

        fun onAddChannel(channelUrl: String)
    }
}
