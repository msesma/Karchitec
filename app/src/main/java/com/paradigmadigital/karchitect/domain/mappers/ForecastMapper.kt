package com.paradigmadigital.karchitect.domain.mappers

import com.paradigmadigital.karchitect.domain.ForecastItem
import java.util.*
import javax.inject.Inject

class ForecastMapper
@Inject
constructor() : Mapper<List<ForecastItem>, WeatherData> {
    override fun map(input: WeatherData): List<ForecastItem> {
        val forecast = mutableListOf<ForecastItem>()
        val apiForecast = input.hourlyForecast ?: listOf<HourlyForecast>()
        for (hourlyForecast in apiForecast) {
            forecast.add(
                    ForecastItem(
                            time = getTime(hourlyForecast.fctTime?.epoch ?: "0"),
                            temp = hourlyForecast.temp?.metric?.toFloatOrNull() ?: 0f,
                            feelslike = hourlyForecast.feelslike?.metric?.toFloatOrNull() ?: 0f,
                            windSpeed = hourlyForecast.wspd?.metric?.toFloatOrNull() ?: 0f,
                            rainProbability = hourlyForecast.pop?.toFloatOrNull() ?: 0f,
                            rainQuantity = hourlyForecast.qpf?.metric?.toFloatOrNull() ?: 0f,
                            snow = hourlyForecast.snow?.metric?.toFloatOrNull() ?: 0f,
                            condition = hourlyForecast.condition ?: "",
                            iconUrl = hourlyForecast.iconUrl ?: "",
                            humidity = hourlyForecast.humidity?.toFloatOrNull() ?: 0f
                    )
            )
        }
        return forecast
    }

    fun getTime(epoch: String) = Date(epoch.toLong() * 1000)
}