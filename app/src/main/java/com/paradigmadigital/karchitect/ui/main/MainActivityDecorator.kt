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
import com.paradigmadigital.karchitect.domain.entities.ChannelList
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
        viewModel.channels.observe(activity, Observer<List<ChannelList>> { showChannels(it) })
    }

    override fun stopRefresh() {
        swipeRefresh.isRefreshing = false
    }

    @OnClick(R.id.fab)
    fun onFabClick() {
        dialog.show(R.string.add_channel, R.string.add_channel_text, { delegate?.onAddChannel(it) })
    }

    private fun showChannels(channels: List<ChannelList>?) {
        list.visibility = if (channels.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        stopRefresh()
        adapter.swap(channels)
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)
    }
}
