package com.paradigmadigital.karchitect.domain.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.paradigmadigital.karchitect.domain.entities.Channel

@Dao
abstract class ChannelsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(channel: Channel)

    @Query("SELECT * FROM channels")
    abstract fun getChannels(): LiveData<List<Channel>>

    @Query("SELECT * FROM channels WHERE linkKey = :p0")
    abstract fun getChannelInfo(linkKey: String): LiveData<Channel>
}
