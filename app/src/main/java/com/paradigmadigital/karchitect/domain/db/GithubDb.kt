package com.paradigmadigital.karchitect.domain.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.paradigmadigital.karchitect.domain.entities.DomContributor
import com.paradigmadigital.karchitect.domain.entities.DomRepo
import com.paradigmadigital.karchitect.domain.entities.DomUser
import com.paradigmadigital.karchitect.domain.entities.RepoSearchResult

@Database(entities =
    arrayOf(DomUser::class, DomRepo::class, DomContributor::class, RepoSearchResult::class), version = 3)
abstract class GithubDb : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun repoDao(): RepoDao
}
