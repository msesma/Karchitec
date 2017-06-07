package com.paradigmadigital.karchitect.ui.main

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import com.paradigmadigital.karchitect.platform.Callback

class MainViewHolder(
        itemView: ViewGroup
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.desc)
    lateinit var desc: TextView
    @BindView(R.id.qtty)
    lateinit var qtty: TextView

    private lateinit var channel: ChannelUiModel
    private var clickListener: Callback<Int>? = null

    init {
        ButterKnife.bind(this, itemView)
    }

    fun bind(channel: ChannelUiModel, clickListener: Callback<Int>?) {
        this.channel = channel
        this.clickListener = clickListener
        configureView()
    }

    private fun configureView() {
        qtty.text = "${channel.count}"
        desc.text = "${channel.title}: ${channel.description}"
    }

    @OnClick(R.id.main_row)
    internal fun onRowClick() {
        clickListener?.invoke(adapterPosition)
    }

}
