package com.paradigmadigital.karchitect.repository

import android.arch.lifecycle.LiveData
import javax.inject.Inject

class ErrorLiveData
@Inject
constructor(
        val executor: MainThreadExecutor
) : LiveData<NetworkError>() {
    fun setNetworkError(value: NetworkError) {
        executor.execute {
            super.setValue(value)
        }
    }
}

