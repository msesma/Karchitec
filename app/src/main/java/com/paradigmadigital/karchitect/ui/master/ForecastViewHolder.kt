package com.paradigmadigital.karchitect.ui.master

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.api.ImageRepository
import com.paradigmadigital.karchitect.domain.ForecastItem
import com.paradigmadigital.karchitect.platform.format
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import java.text.SimpleDateFormat

class ForecastViewHolder(
        itemView: ViewGroup,
        val imageRepo: ImageRepository
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.icon)
    lateinit var icon: ImageView
    @BindView(R.id.temp)
    lateinit var temp: TextView
    @BindView(R.id.data)
    lateinit var data: TextView
    @BindView(R.id.data2)
    lateinit var data2: TextView
    @BindView(R.id.data3)
    lateinit var data3: TextView
    @BindView(R.id.hour)
    lateinit var hour: TextView

    lateinit private var forecastItem: ForecastItem
    private var forecastClickListener: ForecastClickListener? = null

    private val context: Context
    private val resources: Resources

    private val iconTarget = object : Target {

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            icon.setImageBitmap(bitmap)
        }

        override fun onBitmapFailed(errorDrawable: Drawable?) {
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
        }
    }

    init {
        ButterKnife.bind(this, itemView)
        resources = itemView.resources
        context = itemView.context
    }

    fun bind(forecastItem: ForecastItem, forecastClickListener: ForecastClickListener?) {
        this.forecastItem = forecastItem
        this.forecastClickListener = forecastClickListener
        configureView()
    }

    private fun configureView() {
        imageRepo.getCurrentIcon(forecastItem.iconUrl, iconTarget)

        val hr = SimpleDateFormat("HH").format(forecastItem.time).toInt()
        hour.setText("$hr")

        temp.setText(String.format(context.getString(R.string.number), forecastItem.temp))

        var dataText = "ºC "
        if (forecastItem.feelslike != forecastItem.temp) dataText += "(${forecastItem.feelslike}ºC) "
        data.setText(dataText)

        data2.setText("${forecastItem.windSpeed.format(0)} km/h")
        data3.setText("${forecastItem.rainProbability.format(0)}%")
    }

    @OnClick(R.id.forecast_row)
    internal fun onRowClick() {
        forecastClickListener?.onClick(adapterPosition)
    }
}
