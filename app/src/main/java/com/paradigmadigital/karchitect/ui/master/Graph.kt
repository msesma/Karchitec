package com.paradigmadigital.karchitect.ui.master

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.support.annotation.ColorRes
import android.text.format.Time
import android.view.ViewTreeObserver
import android.widget.ImageView
import com.paradigmadigital.karchitect.R
import java.text.SimpleDateFormat
import javax.inject.Inject


class Graph
@Inject
constructor(private val context: Context) {

    companion object {
        val FL_WIDTH = 4f
    }

    private val time = Time()
    private val temps = mutableListOf<Float>()
    private val feelLike = mutableListOf<Float>()
    private val rainsQuantity = mutableListOf<Float>()
    private val rainsProbability = mutableListOf<Float>()

    var imageView: ImageView? = null
    var currentWeather: CurrentWeather? = null
    var forecast: List<ForecastItem> = listOf()
    var astronomy: Astronomy? = null

    val layoutListener = ViewTreeObserver.OnGlobalLayoutListener { drawInternal() }

    fun draw(imageView: ImageView) {
        if (currentWeather == null || astronomy == null || forecast.isEmpty()) {
            return
        }
        this.imageView = imageView
        imageView.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
    }

    fun drawInternal() {
        imageView?.viewTreeObserver?.removeOnGlobalLayoutListener(layoutListener);
        time.setToNow()

        temps.clear()
        feelLike.clear()
        rainsQuantity.clear()
        rainsProbability.clear()

        temps.add(currentWeather!!.temp)
        feelLike.add(currentWeather!!.feelsLike)
        rainsQuantity.add(currentWeather!!.precip1hrMetric)
        rainsProbability.add(if (rainsQuantity[0] > 0) 50f else 0f)
        for (item in forecast) {
            temps.add(item.temp)
            feelLike.add(item.feelslike)
            rainsQuantity.add(item.rainQuantity)
            rainsProbability.add(item.rainProbability)
        }


        val bitmap = Bitmap.createBitmap(imageView!!.width, imageView!!.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)

        drawTemp(canvas)
        drawRain(canvas)

        imageView?.setImageBitmap(bitmap)
    }

    private fun drawTemp(canvas: Canvas) {
        val sunriseH = SimpleDateFormat("HH").format(astronomy?.sunrise).toInt()
        val sunsetH = SimpleDateFormat("HH").format(astronomy?.sunset).toInt()
        val sunriseM = SimpleDateFormat("mm").format(astronomy?.sunrise).toInt()
        val sunsetM = SimpleDateFormat("mm").format(astronomy?.sunset).toInt()
        val hours = temps.size

        val paint = Paint()
        val linePaint = Paint()
        linePaint.color = Color.BLACK
        val feelPaint = Paint()
        feelPaint.color = Color.RED
        feelPaint.strokeWidth = FL_WIDTH

        var min = 100f
        var max = -100f
        for (temp in temps) {
            min = Math.min(min, temp)
            max = Math.max(max, temp)
        }
        val degree = canvas.height * 0.8 / (max - min)
        val height = (canvas.height * 0.9).toFloat()
        val step: Float = 60 * canvas.width / (hours * 60 - time.minute).toFloat()
        var xpos = 0f

        for (i in 0 until hours) {
            temps[i] -= min
            feelLike[i] -= min

            var hour = time.hour + i
            if (hour > 23) hour %= 24

            val ypos = height - (temps[i] * degree).toInt()
            if (i == 0) {
                val isNight = hour < sunriseH || hour > sunsetH
                paint.color = getDaylightColor(isNight)
                canvas.drawRect(xpos, ypos, xpos + step * (60 - time.minute) / 60, canvas.height.toFloat(), paint)
            } else if (hour == sunriseH || hour == sunsetH) {
                val minute = if (hour == sunriseH) sunriseM else sunsetM
                val isNight = hour < sunriseH || hour == sunriseH && minute < sunriseM ||
                        hour > sunsetH || hour == sunsetH && minute >= sunsetM

                val x = xpos + step * (60 - minute) / 60

                paint.color = getDaylightColor(!isNight)
                canvas.drawRect(xpos, ypos, x, canvas.height.toFloat(), paint)
                paint.color = getDaylightColor(isNight)
                canvas.drawRect(x, ypos, xpos + step, canvas.height.toFloat(), paint)
            } else {
                val isNight = hour < sunriseH || hour > sunsetH
                paint.color = getDaylightColor(isNight)
                canvas.drawRect(xpos, ypos, xpos + step, canvas.height.toFloat(), paint)
            }

            if (temps[i] != feelLike[i]) {
                val yposFeel = height - (feelLike[i] * degree).toInt() - FL_WIDTH / 2
                canvas.drawLine(xpos, yposFeel, xpos + step, yposFeel, feelPaint)
            }

            linePaint.textSize = 36f
            if (hour % 6 == 0) {
                canvas.drawText(Integer.toString(hour), xpos, 32f, linePaint)
                canvas.drawLine(xpos, 0f, xpos, canvas.height.toFloat(), linePaint)
            }

            xpos += if (i == 0) step * (60 - time.minute) / 60 else step
        }

        linePaint.textSize = 48f
        var ypos = height - ((max - min) * degree).toInt()
        canvas.drawLine(0f, ypos, canvas.width.toFloat(), ypos, linePaint)
        canvas.drawText(Integer.toString(max.toInt()), 0f, (ypos + 40), linePaint)
        ypos = height
        canvas.drawLine(0f, ypos, canvas.width.toFloat(), ypos, linePaint)
        canvas.drawText(Integer.toString(min.toInt()), 0f, (ypos - 10), linePaint)
    }

    @ColorRes
    private fun getDaylightColor(night: Boolean): Int {
        return if (night) context.resources.getColor(R.color.darkYellow) else Color.YELLOW
    }

    private fun drawRain(canvas: Canvas) {
        val paint = Paint()
        val hours = temps.size
        paint.color = Color.BLUE

        val height = canvas.height.toFloat()
        val degree = canvas.height / 100
        val step: Float = 60 * canvas.width / (hours * 60 - time.minute).toFloat()
        var xpos = 0f

        for (i in 0 until hours) {
            paint.alpha = 96 + rainsQuantity[i].toInt() * 8
            val ypos = height - rainsProbability[i] * degree
            if (i == 0) {
                canvas.drawRect(xpos, ypos, xpos + step * (60 - time.minute) / 60, canvas.height.toFloat(), paint)
                xpos += step * (60 - time.minute) / 60
            } else {
                canvas.drawRect(xpos, ypos, xpos + step, canvas.height.toFloat(), paint)
                xpos += step
            }
        }
    }
}
