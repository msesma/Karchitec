package com.paradigmadigital.karchitect.navigation

import android.content.Intent
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.platform.BaseActivity
import com.paradigmadigital.karchitect.ui.detail.DetailActivity
import javax.inject.Inject


class Navigator
@Inject
constructor(
        val activity: BaseActivity
) {
    fun navigateToDetail(channel: Channel) {
        val intent = Intent(activity, DetailActivity::class.java)
        intent.putExtra(DetailActivity.CHANNEL_KEY, channel.linkKey)
        activity.startActivity(intent)
    }
}