package com.paradigmadigital.karchitect.platform

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import com.paradigmadigital.karchitect.injection.ActivityComponent
import com.paradigmadigital.karchitect.injection.ApplicationComponent
import com.paradigmadigital.karchitect.injection.DaggerActivityComponent


open class BaseActivity : AppCompatActivity() {

    fun Activity.getRootView(): ViewGroup = (this.findViewById(android.R.id.content) as ViewGroup).getChildAt(0) as ViewGroup

    private val applicationComponent: ApplicationComponent
        get() = (application as AndroidApplication).applicationComponent

    lateinit var activityComponent: ActivityComponent

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        activityComponent = DaggerActivityComponent.builder()
                .applicationComponent(applicationComponent)
                .activityModule(ActivityModule(this))
                .build()
    }
}
