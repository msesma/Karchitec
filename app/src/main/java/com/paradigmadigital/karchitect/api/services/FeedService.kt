package com.paradigmadigital.karchitect.api.services

import com.paradigmadigital.karchitect.api.model.Feed
import retrofit2.Call
import retrofit2.http.GET


interface FeedService {
    @GET("/feed/")
    fun getFeed(): Call<Feed>
}
