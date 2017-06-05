package com.paradigmadigital.karchitect.ui.detail

import com.paradigmadigital.karchitect.repository.FeedRepository
import com.paradigmadigital.karchitect.domain.entities.Item
import com.paradigmadigital.karchitect.ui.browser.CustomTabsManager
import javax.inject.Inject

class DetailActivityPresenter
@Inject constructor(
        val browser: CustomTabsManager,
        val repository: FeedRepository
) {

    private var decorator: DetailActivityUserInterface? = null
    private lateinit var viewModel: DetailViewModel

    private val delegate = object : DetailActivityUserInterface.Delegate {
        override fun onClick(item: Item) {
            browser.showContent(item.link)
            repository.markAsRead(item)
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
