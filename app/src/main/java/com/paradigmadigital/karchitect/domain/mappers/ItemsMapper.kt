package com.paradigmadigital.karchitect.domain.mappers

import com.paradigmadigital.karchitect.api.model.Feed
import com.paradigmadigital.karchitect.domain.entities.Item
import javax.inject.Inject

class ItemsMapper
@Inject
constructor() : Mapper<List<Item>, Feed> {
    override fun map(input: Feed): List<Item> {
        val items = mutableListOf<Item>()
        val feedItems = input.channel?.feedItems ?: emptyList()
        val title = input.channel?.title ?: ""
        val desc = input.channel?.description ?: ""
        for (feedItem in feedItems) {
            items.add(
                    Item(
                            channelKey = title + desc,
                            pubDate = feedItem.pubDate ?: "",
                            title = feedItem.title ?: "",
                            link = feedItem.link ?: "",
                            description = feedItem.description ?: ""
                    )
            )
        }
        return items
    }
}