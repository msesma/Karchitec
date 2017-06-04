package com.paradigmadigital.karchitect.ui.detail

import android.content.Context
import android.content.res.Resources
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.domain.entities.Item.Companion.READ

class DetailViewHolder(
        itemView: ViewGroup
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.desc)
    lateinit var desc: TextView
    @BindView(R.id.title)
    lateinit var title: TextView

    lateinit private var item: Item
    private var clickListener: DetailClickListener? = null

    private val context: Context
    private val resources: Resources

    init {
        ButterKnife.bind(this, itemView)
        resources = itemView.resources
        context = itemView.context
    }

    fun bind(item: Item, detailClickListener: DetailClickListener?) {
        this.item = item
        this.clickListener = detailClickListener
        configureView()
    }

    private fun configureView() {
        title.text = item.title
        desc.text = item.description
        val textColor = if (item.read == READ)
            ContextCompat.getColor(title.context, R.color.read) else
            ContextCompat.getColor(title.context, R.color.unread)
        title.setTextColor(textColor)
        desc.setTextColor(textColor)
    }

    @OnClick(R.id.detail_row)
    internal fun onRowClick() {
        clickListener?.onClick(adapterPosition)
    }
}
