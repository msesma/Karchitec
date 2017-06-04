package com.paradigmadigital.karchitect.domain.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.domain.entities.ItemCount

@Dao
abstract class  ItemsDao {

    fun insert(items: List<Item>) {
        for (item in items) {
            insert(item)
        }
    }

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract fun insert(item: Item)

    @Update
    abstract fun  updateItem(item: Item)

    @Query("SELECT * FROM items where channelKey = :p0")
    abstract fun getAll(channelKey: String): LiveData<List<Item>>

    @Query("SELECT count(*) AS count, channelKey FROM items WHERE read = 0 GROUP BY channelKey")
    abstract fun getUnreadCount(): LiveData<List<ItemCount>>
}
