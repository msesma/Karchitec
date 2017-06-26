package com.paradigmadigital.karchitect.repository

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject


class ErrorLiveData
@Inject
constructor(
        val executor: MainThreadExecutor
) : LiveData<NetworkError>() {

    private val pending = AtomicBoolean(false)

    fun setNetworkError(value: NetworkError) {
        executor.execute {
            pending.set(true)
            super.setValue(value)
        }
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<NetworkError>) =
            super.observe(owner, Observer<NetworkError> { value ->
                if (pending.compareAndSet(true, false)) {
                    observer.onChanged(value)
                }
            })
}

