package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "items",
        foreignKeys = arrayOf(ForeignKey(
                entity = Channel::class,
                parentColumns = arrayOf("link"),
                childColumns = arrayOf("channelLink"),
                onDelete = ForeignKey.CASCADE)),
        indices = arrayOf(Index(value = "channelLink")))
class Item(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var channelLink: String,
        var pubDate: String,
        var title: String,
        var link: String,
        var description: String
)