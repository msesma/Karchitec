package com.paradigmadigital.karchitect.domain.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.paradigmadigital.karchitect.domain.entities.Item

@Dao
abstract class ItemsDao {

    fun insert(items: List<Item>) {
        for (item in items) {
            insert(item)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(item: Item)

    @Update
    abstract fun updateItem(item: Item)

    @Query("SELECT * FROM items WHERE channelKey = :channelKey ORDER BY pubDate DESC")
    abstract fun getAll(channelKey: String): LiveData<List<Item>>
}
