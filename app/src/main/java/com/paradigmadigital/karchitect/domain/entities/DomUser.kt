package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity

@Entity(
        primaryKeys = arrayOf("login"))
data class DomUser(
        val login: String,
        val avatarUrl: String,
        val name: String,
        val company: String,
        val reposUrl: String,
        val blog: String)
