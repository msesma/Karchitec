package com.paradigmadigital.karchitect.ui.main

import com.paradigmadigital.karchitect.domain.entities.ChannelList

interface MainActivityUserInterface {

    fun initialize(delegate: Delegate, viewModel: MainViewModel)

    fun stopRefresh()

    interface Delegate {

        fun onRefresh()

        fun onClick(channel: ChannelList)

        fun onAddChannel(channelUrl: String)
    }
}
