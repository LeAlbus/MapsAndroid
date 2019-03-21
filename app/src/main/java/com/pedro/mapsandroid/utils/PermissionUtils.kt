package com.pedro.mapsandroid.utils

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat

object PermissionUtils {

    fun validatePermissions (permissions: List<String>,
                             activity: Activity,
                             requestCode: Int) : Boolean {
        val permissionList = ArrayList<String>()

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M ){
            return true
        } else {

            for (permission in permissions) {
                val hasPermission = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED

                if (!hasPermission) permissionList.add(permission)

                if (permissionList.isEmpty()) return true

                else {
                    val newPermission = arrayOfNulls<String>(permissionList.size)
                    ActivityCompat.requestPermissions(activity, permissionList.toTypedArray(), requestCode)
                }
            }
            return true
        }

        return false
    }
}