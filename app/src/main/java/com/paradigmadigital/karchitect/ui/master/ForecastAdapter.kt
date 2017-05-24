package com.paradigmadigital.karchitect.ui.master

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.api.ImageRepository
import javax.inject.Inject

class ForecastAdapter
@Inject
constructor(
        private val imageRepository: ImageRepository
) : RecyclerView.Adapter<ForecastViewHolder>() {

    private var forecast: List<ForecastItem> = listOf()
    private var forecastClickListener: ForecastClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.forecast_line, parent, false) as ViewGroup
        return ForecastViewHolder(view, imageRepository)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) =
            holder.bind(forecast[position], forecastClickListener)

    override fun getItemCount() = forecast.size

    fun swap(forecast: List<ForecastItem>) {
        this.forecast = forecast
        notifyDataSetChanged()
    }

    fun setClickListener(forecastClickListener: ForecastClickListener) {
        this.forecastClickListener = forecastClickListener
    }

    fun getItemAtPosition(position: Int): ForecastItem {
        return forecast[position]
    }
}
