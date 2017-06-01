package com.paradigmadigital.karchitect.ui.main

import com.paradigmadigital.karchitect.domain.entities.Channel

interface MainActivityUserInterface {

    fun initialize(delegate: Delegate, viewModel: ChannelsViewModel)

    interface Delegate {

        fun onRefresh()

        fun onClick(channel: Channel)

        fun onFab()
    }
}
