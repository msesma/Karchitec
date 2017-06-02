package com.paradigmadigital.karchitect.ui.detail

import com.paradigmadigital.karchitect.domain.entities.Item
import javax.inject.Inject

class DetailActivityPresenter
@Inject constructor() {

    private var decorator: DetailActivityUserInterface? = null
    private lateinit var viewModel: DetailViewModel

    private val delegate = object : DetailActivityUserInterface.Delegate {
        override fun onClick(item: Item) {
            //TODO open WebView with the item link
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
