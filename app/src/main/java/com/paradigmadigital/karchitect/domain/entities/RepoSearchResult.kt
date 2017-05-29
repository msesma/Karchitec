package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.TypeConverters
import com.paradigmadigital.karchitect.domain.db.GithubTypeConverters

@Entity(
        primaryKeys = arrayOf("query"))
@TypeConverters(GithubTypeConverters::class)
data class RepoSearchResult(
        var query: String,
        var repoIds: List<Int>,
        var totalCount: Int,
        var next: Int)
