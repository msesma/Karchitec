package com.paradigmadigital.karchitect.api.model

import com.google.gson.annotations.SerializedName

class RepoSearchResponse {
    @SerializedName("total_count")
    private val total: Int = 0

    @SerializedName("items")
    private val items: List<Repo>? = null
}
