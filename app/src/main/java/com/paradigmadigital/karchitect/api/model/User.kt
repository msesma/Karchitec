package com.paradigmadigital.karchitect.api.model

import com.google.gson.annotations.SerializedName

class User() {
    @SerializedName("login")
    val login: String? = null

    @SerializedName("avatar_url")
    val avatarUrl: String? = null

    @SerializedName("name")
    val name: String? = null

    @SerializedName("company")
    val company: String? = null

    @SerializedName("repos_url")
    val reposUrl: String? = null

    @SerializedName("blog")
    val blog: String? = null
}