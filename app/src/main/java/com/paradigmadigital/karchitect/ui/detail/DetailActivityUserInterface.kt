package com.paradigmadigital.karchitect.ui.detail

import com.paradigmadigital.karchitect.domain.entities.Item

interface DetailActivityUserInterface {

    fun initialize(delegate: Delegate, viewModel: DetailViewModel)

    interface Delegate {

        fun onClick(item: Item)

    }
}
