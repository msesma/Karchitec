package com.paradigmadigital.karchitect.repository

import com.paradigmadigital.karchitect.api.services.FeedService
import com.paradigmadigital.karchitect.domain.db.ChannelsDao
import com.paradigmadigital.karchitect.domain.db.ItemsDao
import com.paradigmadigital.karchitect.domain.mappers.ChannelMapper
import com.paradigmadigital.karchitect.domain.mappers.ItemsMapper
import com.paradigmadigital.karchitect.platform.Callback
import okhttp3.OkHttpClient
import org.xmlpull.v1.XmlPullParserException
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import java.net.UnknownHostException
import java.util.concurrent.Executor
import javax.inject.Inject

class RefreshUseCase
@Inject
constructor(
        val client: OkHttpClient,
        val itemsDao: ItemsDao,
        val channelsDao: ChannelsDao,
        val executor: Executor,
        val channelMapper: ChannelMapper,
        val itemsMapper: ItemsMapper
) {
    fun refreshItems(channelLink: String, callback: Callback<NetworkError>) {
        executor.execute {
            try {
                val feedService = retrofit2.Retrofit.Builder()
                        .client(client)
                        .addConverterFactory(SimpleXmlConverterFactory.create())
                        .baseUrl(channelLink)
                        .build()
                        .create(FeedService::class.java)

                val response = feedService.getFeed("").execute()
                val feed = response.body()
                feed.url = channelLink
                val channel = channelMapper.map(feed)
                val items = itemsMapper.map(feed)

                channelsDao.insert(channel)
                itemsDao.insert(items)
            } catch (e: Throwable) {
                when (e) {
                    is UnknownHostException -> callback(NetworkError.DISCONNECTED)
                    is IllegalArgumentException -> callback(NetworkError.BAD_URL)
                    is XmlPullParserException -> callback(NetworkError.NOT_A_FEED)
                    else -> callback(NetworkError.UNKNOWN)
                }
            }
        }
    }
}