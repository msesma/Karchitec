package com.paradigmadigital.karchitect.ui.main

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.paradigmadigital.karchitect.domain.FeedRepository
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.platform.AndroidApplication
import javax.inject.Inject

data class ChannelsViewModel
constructor(
        val app: Application
) : AndroidViewModel(app) {

    @Inject lateinit var repository: FeedRepository

    val channels: LiveData<List<Channel>>

    init {
        (app as AndroidApplication).applicationComponent.inject(this)
        channels = repository.channels
    }
}
