package com.paradigmadigital.karchitect.ui.main

import android.arch.lifecycle.Observer
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.Channel
import com.paradigmadigital.karchitect.domain.entities.ChannelData
import com.paradigmadigital.karchitect.domain.entities.ItemCount
import com.paradigmadigital.karchitect.platform.BaseActivity
import com.paradigmadigital.karchitect.platform.isNullOrEmpty
import com.paradigmadigital.karchitect.ui.TextAlertDialog
import javax.inject.Inject

class MainActivityDecorator
@Inject
constructor(
        val activity: BaseActivity,
        val layoutManager: LinearLayoutManager,
        val adapter: MainAdapter,
        val dialog: TextAlertDialog
) : MainActivityUserInterface {

    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.main_list)
    lateinit var list: RecyclerView
    @BindView(R.id.swipeRefreshLayout)
    lateinit var swipeRefresh: SwipeRefreshLayout
    @BindView(R.id.fab)
    lateinit var fab: FloatingActionButton

    private var delegate: MainActivityUserInterface.Delegate? = null
    private var channels: List<Channel> = emptyList()
    private var itemCount: List<ItemCount> = emptyList()

    private val channelsClickListener = object : MainClickListener {
        override fun onClick(index: Int) {
            delegate?.onClick(adapter.getItemAtPosition(index))
        }
    }

    fun bind(view: View) {
        ButterKnife.bind(this, view)
        initToolbar()
        list.layoutManager = layoutManager
        list.itemAnimator = DefaultItemAnimator() as RecyclerView.ItemAnimator?
        swipeRefresh.setOnRefreshListener({ delegate?.onRefresh() })
    }

    fun dispose() {
        delegate = null
    }

    override fun initialize(delegate: MainActivityUserInterface.Delegate, viewModel: MainViewModel) {
        this.delegate = delegate
        list.adapter = adapter
        adapter.setClickListener(channelsClickListener)
        viewModel.channels.observe(activity, Observer<List<Channel>> { showChannels(it) })
        viewModel.channelCount.observe(activity, Observer<List<ItemCount>> { showChannelCounts(it) })
    }

    override fun stopRefresh() {
        swipeRefresh.isRefreshing = false
    }

    @OnClick(R.id.fab)
    fun onFabClick() {
        dialog.show(R.string.add_channel, R.string.add_channel_text, { delegate?.onAddChannel(it) })
    }

    private fun showChannels(channels: List<Channel>?) {
        list.visibility = if (channels.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        stopRefresh()
        this.channels = channels ?: this.channels
        refreshView()
    }

    private fun showChannelCounts(itemCount: List<ItemCount>?) {
        this.itemCount = itemCount ?: this.itemCount
        stopRefresh()
        refreshView()
    }

    private fun refreshView() {
        val channelDataList = mutableListOf<ChannelData>()
        channels.forEach { channel ->
            val count = itemCount.firstOrNull { it.channelKey == channel.linkKey }?.count ?: 0
            channelDataList.add(ChannelData(channel, count))
        }
        adapter.swap(channelDataList)
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)
    }
}
