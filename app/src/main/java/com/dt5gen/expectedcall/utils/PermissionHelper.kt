package com.dt5gen.expectedcall.utils

import android.Manifest
import androidx.activity.result.ActivityResultLauncher

object PermissionHelper {

    // Список всех необходимых разрешений
    val requiredPermissions = listOf(
        Manifest.permission.READ_CONTACTS,
        Manifest.permission.READ_CALL_LOG,
        Manifest.permission.READ_PHONE_STATE,
        Manifest.permission.POST_NOTIFICATIONS
    )

    // Функция для запроса всех разрешений
    fun requestAllPermissions(
        launcher: ActivityResultLauncher<Array<String>>
    ) {
        launcher.launch(requiredPermissions.toTypedArray())
    }
}