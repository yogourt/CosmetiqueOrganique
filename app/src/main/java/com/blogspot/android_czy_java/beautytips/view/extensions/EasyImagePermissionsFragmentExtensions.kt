package com.blogspot.android_czy_java.beautytips.view.extensions

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment


private val necessaryPermissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)

const val PERMISSIONS_REQUEST_CODE = 150

fun Fragment.arePermissionsGranted(): Boolean {
    context?.let {
        for (permission in necessaryPermissions) {
            if (ContextCompat.checkSelfPermission(it, permission) != PackageManager.PERMISSION_GRANTED)
                return false

        }
    } ?: return false
    return true
}

fun Fragment.isAnyPermissionGranted(): Boolean {
    context?.let {
        for (permission in necessaryPermissions) {
            if (ContextCompat.checkSelfPermission(it, permission) == PackageManager.PERMISSION_GRANTED)
                return true

        }
    } ?: return false
    return false
}

fun Fragment.requestPermissionsCompat() {
    requestPermissions(necessaryPermissions, PERMISSIONS_REQUEST_CODE)
}


