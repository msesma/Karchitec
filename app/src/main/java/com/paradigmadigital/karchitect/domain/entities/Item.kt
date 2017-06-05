package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

@Entity(tableName = "items")
data class Item(
        @PrimaryKey
        var link: String,
        var channelKey: String,
        var pubDate: Date,
        var title: String,
        var description: String,
        var read: Int
) {
    companion object {
        val READ = 1
    }
}
