package com.paradigmadigital.karchitect.ui.detail

import com.paradigmadigital.karchitect.domain.FeedRepository
import com.paradigmadigital.karchitect.domain.entities.Item
import javax.inject.Inject

class DetailActivityPresenter
@Inject
constructor(
        val repository: FeedRepository
) {

    private var decorator: DetailActivityUserInterface? = null
    private lateinit var viewModel: DetailViewModel

    private val delegate = object : DetailActivityUserInterface.Delegate {

        override fun onRefresh() {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onClick(item: Item) {
            //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    fun initialize(decorator: DetailActivityUserInterface, viewModel: DetailViewModel) {
        this.decorator = decorator
        this.viewModel = viewModel
        this.decorator?.initialize(delegate, viewModel)
    }

    fun dispose() {
        this.decorator = null
    }
}
