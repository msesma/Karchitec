package com.paradigmadigital.karchitect.ui.master

import com.paradigmadigital.karchitect.domain.Astronomy
import com.paradigmadigital.karchitect.domain.CurrentWeather
import com.paradigmadigital.karchitect.domain.ForecastItem

interface MainActivityUserInterface {

    fun initialize(delegate: Delegate)

    fun showError(error: Exception)

    fun showCurrentWeather(currentWeather: CurrentWeather)

    fun showCurrentAstronomy(astronomy: Astronomy)

    fun showForecast(forecast: List<ForecastItem>)

    fun setCity(city: String)

    interface Delegate {

        fun onRefresh()

        fun onClick(forecastItem: ForecastItem)

    }
}
