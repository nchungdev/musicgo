package com.nchungdev.musicgo.permission

import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity


object PermissionUtils {
    fun requestPermission(
        activity: AppCompatActivity, requestCode: Int,
        requestPermissions: List<String> = ArrayList()
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permissions: MutableList<String> = ArrayList()
            requestPermissions.forEach { permission ->
                if (PackageManager.PERMISSION_GRANTED != activity.checkSelfPermission(permission)) {
                    permissions.add(permission)
                }
            }
            if (permissions.size != 0) {
                activity.requestPermissions(
                    permissions.toTypedArray(),
                    requestCode
                )
            }
        }
    }

    private fun isPermissionGranted(
        grantPermissions: Array<String>,
        grantResults: IntArray
    ): Boolean {
        grantPermissions.indices.forEach { i ->
            return grantResults[i] == PackageManager.PERMISSION_GRANTED
        }
        return false
    }

    fun onRequestPermissionResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        isPermissionGranted(permissions, grantResults)
    }
}