package com.dt5gen.expectedcall.utils

import android.Manifest
import android.app.Activity
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.util.Log
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


fun requestDoNotDisturbPermission(context: Context) {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    if (!notificationManager.isNotificationPolicyAccessGranted) {
        Log.d("DND", "Запрос разрешения на изменение режима DND")
        val intent = Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS)
        context.startActivity(intent)
    } else {
        Log.d("DND", "Разрешение на изменение DND уже предоставлено")
    }
}