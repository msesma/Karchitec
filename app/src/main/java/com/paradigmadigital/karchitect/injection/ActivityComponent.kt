package com.paradigmadigital.karchitect.injection

import com.paradigmadigital.karchitect.platform.ActivityModule
import com.paradigmadigital.karchitect.platform.BaseActivity
import com.paradigmadigital.karchitect.ui.main.MainActivity
import dagger.Component

@PerActivity
@Component(dependencies = arrayOf(ApplicationComponent::class), modules = arrayOf(ActivityModule::class))
interface ActivityComponent {

    fun inject(mainActivity: MainActivity)

    //Exposed to sub-graphs.
    fun activity(): BaseActivity
}
