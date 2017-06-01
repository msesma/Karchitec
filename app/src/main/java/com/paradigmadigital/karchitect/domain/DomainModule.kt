package com.paradigmadigital.karchitect.domain

import android.arch.persistence.room.Room
import android.content.Context
import com.paradigmadigital.karchitect.domain.db.ChannelsDao
import com.paradigmadigital.karchitect.domain.db.FeedDb
import com.paradigmadigital.karchitect.domain.db.ItemsDao
import dagger.Module
import dagger.Provides
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Singleton

@Module
class DomainModule() {

    @Singleton
    @Provides
    fun provideFeedDb(application: Context): FeedDb {
        return Room.databaseBuilder(application, FeedDb::class.java, "feed.db").build()
    }

    @Singleton
    @Provides
    fun provideChannelsDao(db: FeedDb): ChannelsDao {
        return db.channelsDao()
    }

    @Singleton
    @Provides
    fun provideItemsDao(db: FeedDb): ItemsDao {
        return db.itemsDao()
    }

    @Singleton
    @Provides
    fun provideExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }
}
