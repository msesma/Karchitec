package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index

@Entity(
        indices = arrayOf(Index("id"), Index("owner_login")),
        primaryKeys = arrayOf("name", "owner_login"))
data class DomRepo(
        var id: Int,
        var name: String,
        var fullName: String,
        var description: String,
        @Embedded(prefix = "owner_")
        var owner: DomOwner,
        var stars: Int) {
}
