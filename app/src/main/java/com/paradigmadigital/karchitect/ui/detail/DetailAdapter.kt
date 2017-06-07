package com.paradigmadigital.karchitect.ui.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.platform.Callback
import javax.inject.Inject

class DetailAdapter
@Inject
constructor() : RecyclerView.Adapter<DetailViewHolder>() {

    private var items: List<Item> = listOf()
    private var clickListener: Callback<Int>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.detail_line, parent, false) as ViewGroup
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) = holder.bind(items[position], clickListener)

    override fun getItemCount() = items.size

    fun swap(items: List<Item>?) {
        items?.let {
            this.items = it
            notifyDataSetChanged()
        }
    }

    fun setClickListener(clickListener: Callback<Int>) {
        this.clickListener = clickListener
    }

    fun getItemAtPosition(position: Int) = items[position]
}

