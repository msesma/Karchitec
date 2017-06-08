package com.paradigmadigital.karchitect.repository

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
        val useCase: RefreshUseCase,
        val errorData: ErrorLiveData
) {

    fun getChannels(): LiveData<List<ChannelUiModel>> {
        refreshItems()
        return channelsDao.getChannelList()
    }

    fun getItems(channelLink: String) = itemsDao.getAll(channelLink)

    fun getErrors(): LiveData<NetworkError> = errorData

    fun addChannel(channelLink: String) = useCase.refreshItems(channelLink, { errorData.setNetworkError(it) })

    fun refreshItems() {
        var links = channelsDao.getChannelsSync().map { (linkKey) -> linkKey }
        if (links.isNullOrEmpty()) links = addSampleChannels()
        for (link in links) {
            useCase.refreshItems(link, { errorData.setNetworkError(it) })
        }
    }

    fun markAsRead(item: Item) {
        item.read = READ
        itemsDao.updateItem(item)
    }

    private fun addSampleChannels() = listOf(
            "http://www.paradigmadigital.com/feed/",
            "http://feed.androidauthority.com/")
}