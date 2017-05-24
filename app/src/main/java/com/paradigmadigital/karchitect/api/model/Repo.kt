package com.paradigmadigital.karchitect.api.model

import com.google.gson.annotations.SerializedName

class Repo {
    @SerializedName("name")
    var name: String? = null

    @SerializedName("full_name")
    var fullName: String? = null

    @SerializedName("description")
    var description: String? = null

    @SerializedName("stargazers_count")
    var stars: Int = 0

    @SerializedName("owner")
    var owner: Owner? = null
}
