package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index

@Entity(
        indices = arrayOf(Index("id"), Index("owner_login")),
        primaryKeys = arrayOf("name", "owner_login"))
data class DomRepo(
        val id: Int,
        val name: String,
        val fullName: String,
        val description: String,
        val owner: DomOwner,
        val stars: Int) {
}
