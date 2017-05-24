package com.paradigmadigital.karchitect.api.model

import com.google.gson.annotations.SerializedName

class Owner {
    @SerializedName("login")
    var login: String? = null
    @SerializedName("url")
    var url: String? = null
}
