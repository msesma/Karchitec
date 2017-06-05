package com.paradigmadigital.karchitect.domain.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.domain.entities.ChannelList

@Dao
abstract class ChannelsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(channel: Channel)

    @Query("SELECT * FROM channels")
    abstract fun getChannelsSync(): List<Channel>

    @Query("SELECT linkKey, title, description, count FROM channels " +
            "LEFT JOIN (SELECT count(*) AS count, channelKey FROM items WHERE read = 0 GROUP BY channelKey) " +
            "ON linkKey = channelKey")
    abstract fun getChannelList(): LiveData<List<ChannelList>>
}
