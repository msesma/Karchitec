package com.paradigmadigital.karchitect.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import javax.inject.Inject

class MainAdapter
@Inject
constructor() : RecyclerView.Adapter<MainViewHolder>() {

    private var channels: List<ChannelUiModel> = listOf()
    private var clickListener: MainClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.main_line, parent, false) as ViewGroup
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) =
            holder.bind(channels[position], clickListener)

    override fun getItemCount() = channels.size

    fun swap(channels: List<ChannelUiModel>?) {
        if (channels != null) {
            this.channels = channels
            notifyDataSetChanged()
        }
    }

    fun setClickListener(forecastClickListener: MainClickListener) {
        this.clickListener = forecastClickListener
    }

    fun getItemAtPosition(position: Int): ChannelUiModel {
        return channels[position]
    }
}
