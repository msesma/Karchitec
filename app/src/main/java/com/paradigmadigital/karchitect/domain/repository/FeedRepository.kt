package com.paradigmadigital.karchitect.domain.repository

import android.arch.lifecycle.LiveData
import com.paradigmadigital.karchitect.domain.db.ChannelsDao
import com.paradigmadigital.karchitect.domain.db.ItemsDao
import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.domain.entities.Item.Companion.READ
import com.paradigmadigital.karchitect.platform.isNullOrEmpty

class FeedRepository
constructor(
        val itemsDao: ItemsDao,
        val channelsDao: ChannelsDao,
        val useCase: RefreshUseCase
) {

    fun getChannels(): LiveData<List<ChannelUiModel>> {
        refreshItems()
        return channelsDao.getChannelList()
    }

    fun addChannel(channelLink: String) = useCase.refreshItems(channelLink)

    fun getItems(channelLink: String): LiveData<List<Item>> {
        return itemsDao.getAll(channelLink)
    }

    fun refreshItems() {
        var links = channelsDao.getChannelsSync().map { channel -> channel.linkKey }
        if (links.isNullOrEmpty()) links = addSampleChannels()
        for (link in links) {
            useCase.refreshItems(link)
        }
    }

    fun markAsRead(item: Item) {
        item.read = READ
        itemsDao.updateItem(item)
    }

    private fun addSampleChannels(): List<String> {
        val links = mutableListOf<String>()
        links.add("http://www.paradigmatecnologico.com/feed/")
        links.add("http://feed.androidauthority.com/")
        return links
    }
}