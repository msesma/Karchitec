package com.paradigmadigital.karchitect.ui.detail

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.Item
import javax.inject.Inject

class DetailAdapter
@Inject
constructor() : RecyclerView.Adapter<DetailViewHolder>() {

    private var items: List<Item> = listOf()
    private var clickListener: DetailClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.detail_line, parent, false) as ViewGroup
        return DetailViewHolder(view)
    }

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) =
            holder.bind(items[position], clickListener)

    override fun getItemCount() = items.size

    fun swap(items: List<Item>?) {
        if (items != null) {
            this.items = items
            notifyDataSetChanged()
        }
    }

    fun setClickListener(clickListener: DetailClickListener) {
        this.clickListener = clickListener
    }

    fun getItemAtPosition(position: Int): Item {
        return items[position]
    }
}
