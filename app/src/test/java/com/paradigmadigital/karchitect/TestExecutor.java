package com.paradigmadigital.karchitect;

import android.support.annotation.NonNull;

import java.util.concurrent.Executor;

public class TestExecutor implements Executor{
    @Override
    public void execute(@NonNull Runnable command) {
        command.run();
    }
}
