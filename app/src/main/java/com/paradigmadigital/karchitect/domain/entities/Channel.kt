package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "channels")
data class Channel(
        @PrimaryKey
        var linkKey: String,
        var title: String,
        var description: String
)

data class ChannelData(
        var channel: Channel,
        var unreadCount: Int
)