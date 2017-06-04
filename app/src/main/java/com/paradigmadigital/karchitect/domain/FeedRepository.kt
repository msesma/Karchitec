package com.paradigmadigital.karchitect.domain

import android.arch.lifecycle.LiveData
import com.paradigmadigital.karchitect.api.services.FeedService
import com.paradigmadigital.karchitect.domain.db.ChannelsDao
import com.paradigmadigital.karchitect.domain.db.ItemsDao
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.domain.entities.ItemCount
import com.paradigmadigital.karchitect.domain.mappers.ChannelMapper
import com.paradigmadigital.karchitect.domain.mappers.ItemsMapper
import com.paradigmadigital.karchitect.platform.isNullOrEmpty
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

    fun getChannels(): LiveData<List<Channel>> {
        refreshItems()
        return channelsDao.getChannels()
    }

    fun addChannel(channelLink: String) = refreshItems(channelLink)

    fun getItems(channelLink: String): LiveData<List<Item>> {
        return itemsDao.getAll(channelLink)
    }

    fun getUnreadItemCount(): LiveData<List<ItemCount>> {
        return itemsDao.getUnreadCount()
    }

    fun refreshItems() {
        val channels = channelsDao.getChannelsSync()
        if (channels.isNullOrEmpty()) addSampleChannels()
        for (channel in channels) {
            refreshItems(channel.linkKey)
        }
    }

    private fun addSampleChannels() {
        addChannel("http://www.paradigmatecnologico.com/feed/")
        addChannel("http://feed.androidauthority.com/")
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