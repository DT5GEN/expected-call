package com.dt5gen.expectedcall.utiils

import android.Manifest
import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat

abstract class PermissionHelper {
    fun requestPermissions(context: Context) {
        val permissions = arrayOf(
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.MODIFY_AUDIO_SETTINGS
        )
        ActivityCompat.requestPermissions(
            context as Activity,
            permissions,
            1
        )
    }
}