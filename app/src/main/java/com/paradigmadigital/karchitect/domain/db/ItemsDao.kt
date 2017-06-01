package com.paradigmadigital.karchitect.domain.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.paradigmadigital.karchitect.domain.entities.Item

@Dao
abstract class  ItemsDao {

    fun insert(items: List<Item>) {
        for (item in items) {
            insert(item)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(item: Item)

    @Query("SELECT * FROM items where channelKey = :p0")
    abstract fun getAll(channelKey: String): LiveData<List<Item>>
}
