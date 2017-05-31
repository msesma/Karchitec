package com.paradigmadigital.karchitect.ui.main

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.paraguas.ui.master.ChannelsClickListener
import javax.inject.Inject

class MainActivityDecorator
@Inject
constructor(
        val activity: AppCompatActivity,
        val layoutManager: LinearLayoutManager,
        val adapter: ChannelsAdapter
) : MainActivityUserInterface {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.forecast_list)
    lateinit var list: RecyclerView
    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefresh: SwipeRefreshLayout


    private var delegate: MainActivityUserInterface.Delegate? = null

    private val channelsClickListener = object : ChannelsClickListener {
        override fun onClick(index: Int) {
            delegate?.onClick(adapter.getItemAtPosition(index))
        }
    }

    internal var refreshListener: SwipeRefreshLayout.OnRefreshListener = SwipeRefreshLayout.OnRefreshListener { delegate?.onRefresh() }


    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
        list.layoutManager = layoutManager
        list.itemAnimator = DefaultItemAnimator()
        swipeRefresh.setOnRefreshListener(refreshListener)
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: MainActivityUserInterface.Delegate, viewModel: ChannelsViewModel) {
        this.delegate = delegate
        toolbar.title = ""
        list.adapter = adapter
        adapter.setClickListener(channelsClickListener)
    }

    private fun showChannels(channels: List<Channel>) {
        list.visibility = if (channels.isEmpty()) View.INVISIBLE else View.VISIBLE
        adapter.swap(channels)
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.setIcon(R.mipmap.ic_launcher)
    }
}
