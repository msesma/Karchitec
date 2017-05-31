package com.paradigmadigital.karchitect.domain.mappers

import com.paradigmadigital.karchitect.api.model.Feed
import com.paradigmadigital.karchitect.domain.entities.Channel
import javax.inject.Inject

class ChannelMapper
@Inject
constructor() : Mapper<Channel, Feed> {
    override fun map(input: Feed): Channel {
        return Channel(input.channel?.link ?: "",
                input.channel?.title ?: "",
                input.channel?.description ?: "")
    }
}