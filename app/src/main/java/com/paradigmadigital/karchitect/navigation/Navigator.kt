package com.paradigmadigital.karchitect.navigation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.ForecastItem
import com.paradigmadigital.karchitect.ui.detail.DetailActivity
import javax.inject.Inject


class Navigator
@Inject
constructor(
        val activity: AppCompatActivity
) {
    fun navigateToDetail(forecastItem: ForecastItem) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(activity.getString(R.string.item_key), forecastItem)
        activity.startActivity(intent)
    }
}