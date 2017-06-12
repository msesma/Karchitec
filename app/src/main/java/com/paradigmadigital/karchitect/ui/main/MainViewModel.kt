package com.paradigmadigital.karchitect.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import com.paradigmadigital.karchitect.platform.AndroidApplication
import com.paradigmadigital.karchitect.repository.FeedRepository
import com.paradigmadigital.karchitect.repository.NetworkError
import javax.inject.Inject

data class MainViewModel
constructor(
        val app: Application
) : AndroidViewModel(app) {

    @Inject lateinit var repository: FeedRepository

    val channels: LiveData<List<ChannelUiModel>>
    val errors: LiveData<NetworkError>

    init {
        (app as AndroidApplication).applicationComponent.inject(this)
        channels = repository.getChannels()
        errors = repository.getErrors()
    }

    fun refreshItems() {
        repository.refreshItems()
    }

    fun addChannel(channelLink: String) {
        repository.addChannel(channelLink)
    }
}
