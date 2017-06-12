package com.paradigmadigital.karchitect.ui.detail

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.platform.AndroidApplication
import com.paradigmadigital.karchitect.repository.FeedRepository
import javax.inject.Inject

data class DetailViewModel
constructor(
        val app: Application
) : AndroidViewModel(app) {

    @Inject lateinit var repository: FeedRepository

    var items: LiveData<List<Item>>? = null

    init {
        (app as AndroidApplication).applicationComponent.inject(this)
    }

    fun initialize(channelLink: String) {
        items = repository.getItems(channelLink)
    }

    fun markAsRead(item: Item) {
        repository.markAsRead(item)
    }
}
