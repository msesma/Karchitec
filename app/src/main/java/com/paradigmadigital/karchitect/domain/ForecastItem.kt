package com.paradigmadigital.karchitect.domain

import java.io.Serializable
import java.util.*

data class ForecastItem(
        val time: Date? = null,
        val temp: Float = 0f,
        val feelslike: Float = 0f,
        val windSpeed: Float = 0f,
        val rainQuantity: Float = 0f,
        val rainProbability: Float = 0f,
        val snow: Float = 0f,
        val condition: String = "",
        val iconUrl: String = "",
        val humidity: Float? = 0f
) : Serializable
