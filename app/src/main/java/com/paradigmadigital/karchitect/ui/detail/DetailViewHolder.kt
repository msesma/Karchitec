package com.paradigmadigital.karchitect.ui.detail

import android.content.Context
import android.content.res.Resources
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.Item

class DetailViewHolder(
        itemView: ViewGroup
) : RecyclerView.ViewHolder(itemView) {

    @BindView(R.id.desc)
    lateinit var desc: TextView
    @BindView(R.id.qtty)
    lateinit var qtty: TextView

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
//        qtty.text = "${item.qtty}"
//        desc.text = "${item.title}: ${item.description}"
    }

    @OnClick(R.id.detail_row)
    internal fun onRowClick() {
        clickListener?.onClick(adapterPosition)
    }
}
