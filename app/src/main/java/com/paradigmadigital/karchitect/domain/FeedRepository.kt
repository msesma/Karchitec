package com.paradigmadigital.karchitect.domain

import android.arch.lifecycle.LiveData
import com.paradigmadigital.karchitect.api.model.Feed
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
import java.io.IOException
import java.util.concurrent.Executor
import javax.inject.Inject

class FeedRepository @Inject
constructor(
        private val client: OkHttpClient,
        private val channelsDao: ChannelsDao,
        private val itemsDao: ItemsDao,
        private val executor: Executor,
        private val channelMapper: ChannelMapper,
        private val itemsMapper: ItemsMapper) {

    val channels: LiveData<List<Channel>>
        get() = channelsDao.getChannels()

    fun getItems(channelLink: String): LiveData<List<Item>> {
        refreshItems(channelLink)
        return itemsDao.getAll(channelLink)
    }

    private fun refreshItems(channelLink: String) {
        executor.execute {
            val feedService= Retrofit.Builder()
                    .client(client)
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .baseUrl(channelLink)
                    .build()
                    .create(FeedService::class.java)

            try {
                val response = feedService.getFeed().execute()
                val feed = response.body() as Feed
                val channel = channelMapper.map(feed)
                val items = itemsMapper.map(feed)

                channelsDao.insert(channel)
                itemsDao.insert(items)
            } catch (e: IOException) {
                // TODO check for error etc.
            }
        }
    }
}