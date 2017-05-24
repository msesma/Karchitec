package com.paradigmadigital.karchitect.platform

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.paradigmadigital.karchitect.R
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class PermissionManager
@Inject
constructor(
        private val context: Context,
        private val activity: AppCompatActivity) {

    companion object {
        private val REQUEST_LOCATION_PERMISSION = 1
    }

    val locationPremission: Boolean
        get() {
            if (EasyPermissions.hasPermissions(context, ACCESS_COARSE_LOCATION)) {
                return true
            }

            if (EasyPermissions.somePermissionPermanentlyDenied(activity, listOf(ACCESS_COARSE_LOCATION))) {
                AppSettingsDialog.Builder(activity).build().show()
                return false
            }

            EasyPermissions.requestPermissions(
                    activity,
                    context.getString(R.string.location_permission_request),
                    REQUEST_LOCATION_PERMISSION,
                    ACCESS_COARSE_LOCATION)
            return false
        }
}
