package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey

@Entity(primaryKeys = arrayOf("repoName", "repoOwner", "login"),
        foreignKeys = arrayOf(ForeignKey(
                entity = DomRepo::class,
                parentColumns = arrayOf("name", "owner_login"),
                childColumns = arrayOf("repoName", "repoOwner"),
                onUpdate = ForeignKey.CASCADE,
                deferred = true)))
class DomContributor(
        var login: String,
        var contributions: Int,
        var avatarUrl: String,
        var repoName: String,
        var repoOwner: String
)
