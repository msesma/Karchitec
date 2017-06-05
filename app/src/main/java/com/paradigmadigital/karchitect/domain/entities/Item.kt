package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "items")
class Item(
        @PrimaryKey
        var link: String,
        var channelKey: String,
        var pubDate: String,
        var title: String,
        var description: String,
        var read: Int
) {
    companion object {
        val READ = 1
    }
}
