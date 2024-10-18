// PermissionHelper.kt
package com.dt5gen.expectedcall.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat

object PermissionHelper {

    const val CONTACTS_PERMISSION = Manifest.permission.READ_CONTACTS
    const val CALL_LOG_PERMISSION = Manifest.permission.READ_CALL_LOG

    fun hasPermission(context: Context, permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission(
        launcher: ActivityResultLauncher<String>,
        permission: String
    ) {
        launcher.launch(permission)
    }

    fun checkAndRequestPermissions(
        context: Context,
        permissions: List<String>,
        launcher: ActivityResultLauncher<String>,
        onPermissionsChecked: (Boolean) -> Unit
    ) {
        val ungrantedPermissions = permissions.filter {
            !hasPermission(context, it)
        }

        if (ungrantedPermissions.isEmpty()) {
            onPermissionsChecked(true)
        } else {
            ungrantedPermissions.forEach { launcher.launch(it) }
        }
    }
}