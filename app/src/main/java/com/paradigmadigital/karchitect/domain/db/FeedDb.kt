package com.paradigmadigital.karchitect.domain.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.domain.entities.Item

@Database(entities = arrayOf(Channel::class, Item::class), version = 1)
abstract class FeedDb : RoomDatabase() {

    abstract fun channelsDao(): ChannelsDao

    abstract fun itemsDao(): ItemsDao
}
