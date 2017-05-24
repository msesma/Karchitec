package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.TypeConverters
import com.paradigmadigital.karchitect.domain.db.GithubTypeConverters

@Entity(
        primaryKeys = arrayOf("query"))
@TypeConverters(GithubTypeConverters::class)
data class RepoSearchResult(
        val query: String,
        val repoIds: List<Int>,
        val totalCount: Int,
        val next: Int?)
