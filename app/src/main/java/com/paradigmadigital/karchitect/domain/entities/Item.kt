package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "items",
        foreignKeys = arrayOf(ForeignKey(
                entity = Channel::class,
                parentColumns = arrayOf("linkKey"),
                childColumns = arrayOf("channelKey"),
                onDelete = ForeignKey.CASCADE)),
        indices = arrayOf(Index(value = "channelKey")))
class Item(
        @PrimaryKey
        var link: String,
        var channelKey: String,
        var pubDate: String,
        var title: String,
        var description: String
)