package com.paradigmadigital.karchitect.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.Channel

import com.paradigmadigital.paraguas.ui.master.ChannelsClickListener
import javax.inject.Inject

class ChannelsAdapter
@Inject
constructor() : RecyclerView.Adapter<ChannelsViewHolder>() {

    private var channels: List<Channel> = listOf()
    private var forecastClickListener: ChannelsClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelsViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.channel_line, parent, false) as ViewGroup
        return ChannelsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChannelsViewHolder, position: Int) =
            holder.bind(channels[position], forecastClickListener)

    override fun getItemCount() = channels.size

    fun swap(channels: List<Channel>?) {
        if (channels != null) {
            this.channels = channels
            notifyDataSetChanged()
        }
    }

    fun setClickListener(forecastClickListener: ChannelsClickListener) {
        this.forecastClickListener = forecastClickListener
    }

    fun getItemAtPosition(position: Int): Channel {
        return channels[position]
    }
}
