package com.paradigmadigital.karchitect.platform

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.paradigmadigital.karchitect.injection.PerActivity
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @PerActivity
    internal fun activity(): AppCompatActivity = this.activity

    @Provides
    @PerActivity
    internal fun provideLinearLayoutManager(activity: AppCompatActivity) = LinearLayoutManager(activity as Context)
}
