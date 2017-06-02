package com.paradigmadigital.karchitect.platform

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.paradigmadigital.karchitect.injection.PerActivity
import com.paradigmadigital.karchitect.ui.TextAlertDialog
import dagger.Module
import dagger.Provides

@Module
class ActivityModule(private val activity: BaseActivity) {

    @Provides
    @PerActivity
    internal fun activity(): BaseActivity = this.activity

    @Provides
    @PerActivity
    internal fun provideLinearLayoutManager(activity: BaseActivity) = LinearLayoutManager(activity as Context)

    @Provides
    @PerActivity
    internal fun provideTextAlertDialog(activity: BaseActivity) = TextAlertDialog(activity)
}
