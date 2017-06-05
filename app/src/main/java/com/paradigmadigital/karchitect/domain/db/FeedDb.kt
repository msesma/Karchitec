package com.paradigmadigital.karchitect.domain.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.paradigmadigital.karchitect.domain.converter.DateConverter
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.domain.entities.Item

@Database(entities = arrayOf(Channel::class, Item::class),
        version = 1,
        exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class FeedDb : RoomDatabase() {

    internal val DATABASE_NAME = "feeddb"

    abstract fun channelsDao(): ChannelsDao

    abstract fun itemsDao(): ItemsDao
}
