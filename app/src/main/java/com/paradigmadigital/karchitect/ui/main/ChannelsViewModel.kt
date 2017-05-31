package com.paradigmadigital.karchitect.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.paradigmadigital.karchitect.domain.FeedRepository
import com.paradigmadigital.karchitect.domain.entities.Channel
import javax.inject.Inject

data class ChannelsViewModel
@Inject
constructor(
        val repository: FeedRepository
) : ViewModel() {
    val channels: LiveData<List<Channel>> = repository.channels
}
