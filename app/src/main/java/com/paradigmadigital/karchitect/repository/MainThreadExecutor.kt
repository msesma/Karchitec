package com.paradigmadigital.karchitect.repository

import android.os.Handler
import android.os.Looper
import java.util.concurrent.Executor
import javax.inject.Inject

class MainThreadExecutor
@Inject
constructor() : Executor {

    val mainThreadHandler = Handler(Looper.getMainLooper())

    override fun execute(command: Runnable) {
        mainThreadHandler.post(command)
    }
}