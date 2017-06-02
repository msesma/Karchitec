package com.paradigmadigital.karchitect.domain

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.paradigmadigital.karchitect.api.services.FeedService
import com.paradigmadigital.karchitect.domain.db.ChannelsDao
import com.paradigmadigital.karchitect.domain.db.ItemsDao
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.domain.mappers.ChannelMapper
import com.paradigmadigital.karchitect.domain.mappers.ItemsMapper
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.util.concurrent.Executor

class FeedRepository
constructor(
        val client: OkHttpClient,
        val itemsDao: ItemsDao,
        val channelsDao: ChannelsDao,
        val executor: Executor,
        val channelMapper: ChannelMapper,
        val itemsMapper: ItemsMapper
) {

    var channelLiveData: LiveData<List<Channel>>? = null
    val observer = Observer<List<Channel>>{ refreshItems(it) }

    fun getChannels(): LiveData<List<Channel>> {
        if (channelLiveData == null) {
            channelLiveData = channelsDao.getChannels()
            channelLiveData?.observeForever(observer)
        }
        return channelLiveData!!
    }

    fun addChannel(channelLink: String) = refreshItems(channelLink)

    fun getItems(channelLink: String): LiveData<List<Item>> {
        return itemsDao.getAll(channelLink)
    }

    fun refreshItems() {
        refreshItems(channelLiveData?.value ?: emptyList())
    }

    private fun refreshItems(channels: List<Channel>?) {
        if (channels != null) {
            for (channel in channels) {
                refreshItems(channel.linkKey)
            }
        }
        channelLiveData?.removeObserver(observer)
    }

    private fun refreshItems(channelLink: String) {
        executor.execute {
            val feedService = Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .baseUrl(channelLink)
                    .build()
                    .create(FeedService::class.java)

            try {
                val response = feedService.getFeed("").execute()
                val feed = response.body()
                feed.url = channelLink
                val channel = channelMapper.map(feed)
                val items = itemsMapper.map(feed)

                channelsDao.insert(channel)
                itemsDao.insert(items)
            } catch (e: Throwable) {
                // TODO check for error etc.
            }
        }
    }


}