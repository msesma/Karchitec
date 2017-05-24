package com.paradigmadigital.karchitect.ui.detail

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.api.ImageRepository
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.text.SimpleDateFormat
import javax.inject.Inject

class DetailActivityDecorator
@Inject
constructor(
        val activity: AppCompatActivity,
        val imageRepo: ImageRepository
) : DetailActivityUserInterface {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.icon)
    lateinit var icon: ImageView
    @BindView(R.id.condition)
    lateinit var tvcondition: TextView
    @BindView(R.id.temp)
    lateinit var tvtemp: TextView
    @BindView(R.id.feelslike)
    lateinit var tvfeelslike: TextView
    @BindView(R.id.rain)
    lateinit var rain: TextView
    @BindView(R.id.humidity)
    lateinit var humidity: TextView
    @BindView(R.id.snow)
    lateinit var snow: TextView
    @BindView(R.id.wind)
    lateinit var wind: TextView

    private var city: String? = null

    private val iconTarget = object : Target {

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            icon.setImageBitmap(bitmap)
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }
    }

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
    }

    override fun initialize(forecastItem: ForecastItem?) {
        toolbar.title = SimpleDateFormat("HH:00").format(forecastItem?.time)
        showForecast(forecastItem)
    }

    private fun showForecast(forecast: ForecastItem?) {
        val url = forecast?.iconUrl ?: ""
        imageRepo.getCurrentIcon(url, iconTarget)
        tvcondition.setText(forecast?.condition)
        tvtemp.setText(String.format(activity.getString(R.string.temp), forecast?.temp))
        tvfeelslike.setText(String.format(activity.getString(R.string.feels_like), forecast?.feelslike))
        rain.setText(String.format(activity.getString(R.string.rain), forecast?.rainProbability, forecast?.rainQuantity))
        humidity.setText(String.format(activity.getString(R.string.humidity), forecast?.humidity))
        snow.setText(String.format(activity.getString(R.string.snow), forecast?.snow))
        wind.setText(String.format(activity.getString(R.string.wind), forecast?.windSpeed))
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}
