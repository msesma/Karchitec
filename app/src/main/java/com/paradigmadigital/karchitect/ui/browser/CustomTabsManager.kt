package com.paradigmadigital.karchitect.ui.browser

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.support.customtabs.CustomTabsCallback
import android.support.customtabs.CustomTabsClient
import android.support.customtabs.CustomTabsIntent
import android.support.v4.content.ContextCompat
import com.paradigmadigital.karchitect.R
import com.paradigmadigital.karchitect.platform.BaseActivity
import javax.inject.Inject

class CustomTabsManager
@Inject
constructor(
        private val activity: BaseActivity,
        private val customTabsHelper: CustomTabsHelper
) : CustomTabsCallback(), ServiceConnection.ServiceConnectionCallback {

    private var connection: ServiceConnection? = null

    private val intent: CustomTabsIntent
        get() {
            val builder = CustomTabsIntent.Builder()
            builder.setToolbarColor(ContextCompat.getColor(activity, R.color.colorPrimary))
            builder.setShowTitle(true)
            builder.setCloseButtonIcon(BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_arrow_back_white_24dp));
            builder.setExitAnimations(activity, android.R.anim.slide_in_left, android.R.anim.slide_out_right)
            return builder.build()
        }

    fun showContent(url: String) {
        val browser = customTabsHelper.getPackageNameToUse()
        if (browser != null) {
            openCustomTab(browser, url)
            return
        }
        fallBackExternalBrowser(url)
    }

    override fun onServiceConnected(client: CustomTabsClient) {}

    override fun onServiceDisconnected() {}

    override fun onNavigationEvent(navigationEvent: Int, extras: Bundle?) {
        if (navigationEvent == CustomTabsCallback.NAVIGATION_ABORTED ||
                navigationEvent == CustomTabsCallback.TAB_HIDDEN ||
                navigationEvent == CustomTabsCallback.NAVIGATION_FAILED) {
            unbindCustomTabsService()
        }
    }

    private fun openCustomTab(browser: String, url: String) {
        connection = ServiceConnection(this)
        CustomTabsClient.bindCustomTabsService(activity, browser, connection)
        val customTabsIntent = intent
        customTabsIntent.intent.`package` = browser
        customTabsIntent.launchUrl(activity, Uri.parse(url))
    }

    private fun unbindCustomTabsService() {
        if (connection == null) {
            return
        }
        activity.unbindService(connection)
    }

    private fun fallBackExternalBrowser(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        activity.startActivity(intent)
    }
}
