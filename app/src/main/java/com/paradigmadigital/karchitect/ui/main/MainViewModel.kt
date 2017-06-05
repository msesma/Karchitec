package com.paradigmadigital.karchitect.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.paradigmadigital.karchitect.domain.FeedRepository
import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import com.paradigmadigital.karchitect.platform.AndroidApplication
import javax.inject.Inject

data class MainViewModel
constructor(
        val app: Application
) : AndroidViewModel(app) {

    @Inject lateinit var repository: FeedRepository

    val channels: LiveData<List<ChannelUiModel>>

    init {
        (app as AndroidApplication).applicationComponent.inject(this)
        channels = repository.getChannels()
    }
}
