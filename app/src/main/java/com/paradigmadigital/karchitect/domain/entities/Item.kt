package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.*

@Entity(tableName = "items")
class Item(
        @PrimaryKey
        var link: String,
        var channelKey: String,
        var pubDate: String,
        var title: String,
        var description: String,
        var read: Int
)

class ItemCount(
        @ColumnInfo
        var count: Int?,
        @ColumnInfo
        var channelKey: String?
)