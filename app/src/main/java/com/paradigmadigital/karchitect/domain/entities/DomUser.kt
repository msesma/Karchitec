package com.paradigmadigital.karchitect.domain.entities

import android.arch.persistence.room.Entity

@Entity(
        primaryKeys = arrayOf("login"))
data class DomUser(
        var login: String,
        var avatarUrl: String,
        var name: String,
        var company: String,
        var reposUrl: String,
        var blog: String)
