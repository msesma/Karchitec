package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "channels")
class Channel(
        @PrimaryKey
        var link: String,
        var title: String,
        var description: String
)
