package com.paradigmadigital.karchitect.ui.main

import android.arch.lifecycle.Observer
import android.support.annotation.StringRes
import android.support.design.widget.FloatingActionButton
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.Toast
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.domain.entities.ChannelUiModel
import com.paradigmadigital.karchitect.platform.isNullOrEmpty
import com.paradigmadigital.karchitect.repository.NetworkError
import com.paradigmadigital.karchitect.repository.NetworkError.*
import com.paradigmadigital.karchitect.ui.BaseActivity
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
        adapter.setClickListener({ delegate.onClick(adapter.getItemAtPosition(it)) })

        viewModel.channels.observe(activity, Observer<List<ChannelUiModel>> { showChannels(it) })
        viewModel.errors.observe(activity, Observer<NetworkError> { showErrors(it) })
    }

    @OnClick(R.id.fab)
    fun onFabClick() = dialog.show(R.string.add_channel, R.string.add_channel_text, { delegate?.onAddChannel(it) })

    private fun showChannels(channels: List<ChannelUiModel>?) {
        list.visibility = if (channels.isNullOrEmpty()) View.INVISIBLE else View.VISIBLE
        stopRefresh()
        adapter.swap(channels)
    }

    private fun showErrors(error: NetworkError?) {
        stopRefresh()
        if (error == null) return
        when (error) {
            SUCCESS -> Unit
            DISCONNECTED -> showToast(R.string.net_error)
            BAD_URL -> showToast(R.string.url_error)
            NOT_A_FEED -> showToast(R.string.feed_error)
            UNKNOWN -> showToast(R.string.unknown_error)
        }
    }

    private fun initToolbar() {
        activity.setSupportActionBar(toolbar)
        val actionBar = activity.supportActionBar
        actionBar?.setDisplayShowTitleEnabled(true)
        actionBar?.setIcon(R.mipmap.ic_launcher)
    }

    private fun showToast(@StringRes text: Int) = Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()

    private fun stopRefresh() {
        swipeRefresh.isRefreshing = false
    }
}
