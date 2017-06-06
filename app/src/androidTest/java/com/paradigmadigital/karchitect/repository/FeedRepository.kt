package com.paradigmadigital.karchitect.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.paradigmadigital.karchitect.domain.db.ChannelsDao
import com.paradigmadigital.karchitect.domain.db.ItemsDao
import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import com.paradigmadigital.karchitect.domain.entities.Item
import java.util.*

//Mock
class FeedRepository
constructor(
        val itemsDao: ItemsDao,
        val channelsDao: ChannelsDao,
        val useCase: RefreshUseCase,
        val errorData: ErrorLiveData
) {

    private val channelUiModel =
            ChannelUiModel("http://feed.test.com", "Channel title", "Channel description", 7)
    private val item =
            Item("http://feed.test.com/item", "http://feed.test.com", Date(), "Item title", "Item description", 0)

    fun getItems(channelLink: String): LiveData<List<Item>> {
        val data = MutableLiveData<List<Item>>()
        data.value = listOf(item)
        return data
    }


    fun getChannels(): LiveData<List<ChannelUiModel>> {
        val data = MutableLiveData<List<ChannelUiModel>>()
        data.value = listOf(channelUiModel)
        return data
    }
}