package com.paradigmadigital.karchitect.ui.browser

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.MATCH_ALL
import android.net.Uri
import android.os.Build
import android.text.TextUtils
import com.paradigmadigital.karchitect.platform.isNullOrEmpty
import java.util.*
import javax.inject.Inject

class CustomTabsHelper
@Inject
constructor(
        private val context: Context,
        private val packageManager: PackageManager
) {
    companion object {
        private val TAG = CustomTabsHelper::class.java.simpleName
        private val STABLE_PACKAGE = "com.android.chrome"
        private val BETA_PACKAGE = "com.chrome.beta"
        private val DEV_PACKAGE = "com.chrome.dev"
        private val LOCAL_PACKAGE = "com.google.android.apps.chrome"
        private val ACTION_CUSTOM_TABS_CONNECTION = "android.support.customtabs.action.CustomTabsService"
    }

    private var packageNameToUse: String? = null

    fun getPackageNameToUse(): String? {
        if (packageNameToUse != null) return packageNameToUse

        val activityIntent = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.paradigmadigital.com"))
        val defaultViewHandlerInfo = packageManager.resolveActivity(activityIntent, 0)
        var defaultViewHandlerPackageName: String? = null
        defaultViewHandlerInfo?.let { defaultViewHandlerPackageName = it.activityInfo.packageName }

        val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) MATCH_ALL else 0
        val resolvedActivityList = packageManager.queryIntentActivities(activityIntent, flags)
        val packagesSupportingCustomTabs = ArrayList<String>()
        for (info in resolvedActivityList) {
            val serviceIntent = Intent()
            serviceIntent.action = ACTION_CUSTOM_TABS_CONNECTION
            serviceIntent.`package` = info.activityInfo.packageName
            if (packageManager.resolveService(serviceIntent, 0) != null) {
                packagesSupportingCustomTabs.add(info.activityInfo.packageName)
            }
        }

        when {
            packagesSupportingCustomTabs.isEmpty() -> packageNameToUse = null
            packagesSupportingCustomTabs.size == 1 -> packageNameToUse = packagesSupportingCustomTabs[0]
            !TextUtils.isEmpty(defaultViewHandlerPackageName)
                    && !hasSpecializedHandlerIntents(context, activityIntent)
                    && packagesSupportingCustomTabs.contains(defaultViewHandlerPackageName) -> packageNameToUse = defaultViewHandlerPackageName
            packagesSupportingCustomTabs.contains(STABLE_PACKAGE) -> packageNameToUse = STABLE_PACKAGE
            packagesSupportingCustomTabs.contains(BETA_PACKAGE) -> packageNameToUse = BETA_PACKAGE
            packagesSupportingCustomTabs.contains(DEV_PACKAGE) -> packageNameToUse = DEV_PACKAGE
            packagesSupportingCustomTabs.contains(LOCAL_PACKAGE) -> packageNameToUse = LOCAL_PACKAGE
        }
        return packageNameToUse
    }

    private fun hasSpecializedHandlerIntents(context: Context, intent: Intent): Boolean {
        val pm = context.packageManager
        val handlers = pm.queryIntentActivities(
                intent,
                PackageManager.GET_RESOLVED_FILTER)
        if (handlers.isNullOrEmpty()) return false

        for (resolveInfo in handlers) {
            val filter = resolveInfo.filter ?: continue
            if (filter.countDataAuthorities() == 0 || filter.countDataPaths() == 0) continue
            if (resolveInfo.activityInfo == null) continue
            return true
        }

        return false
    }
}
