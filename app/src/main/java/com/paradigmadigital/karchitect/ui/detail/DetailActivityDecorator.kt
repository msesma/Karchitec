package com.paradigmadigital.karchitect.ui.detail

import android.arch.lifecycle.Observer
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.platform.BaseActivity
import javax.inject.Inject

class DetailActivityDecorator
@Inject
constructor(
        val activity: BaseActivity,
        val layoutManager: LinearLayoutManager,
        val adapter: DetailAdapter
) : DetailActivityUserInterface {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.detail_list)
    lateinit var list: RecyclerView

    private var delegate: DetailActivityUserInterface.Delegate? = null

    private val clickListener = object : DetailClickListener {
        override fun onClick(index: Int) {
            delegate?.onClick(adapter.getItemAtPosition(index))
        }
    }

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
        list.layoutManager = layoutManager
        list.itemAnimator = DefaultItemAnimator()
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: DetailActivityUserInterface.Delegate, viewModel: DetailViewModel) {
        this.delegate = delegate
        list.adapter = adapter
        adapter.setClickListener(clickListener)
        viewModel.items?.observe(activity, Observer<List<Item>> { it -> adapter.swap(it) })
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)
    }
}
