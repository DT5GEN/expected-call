package com.dt5gen.expectedcall.viewModels

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dt5gen.expectedcall.utils.PermissionHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PermissionViewModel : ViewModel() {

    private val _isAllPermissionsGranted = MutableStateFlow(false)
    val isAllPermissionsGranted: StateFlow<Boolean> = _isAllPermissionsGranted

    // Проверка предоставленных разрешений
    fun checkPermissions(context: Context) {
        viewModelScope.launch {
            val allGranted = PermissionHelper.requiredPermissions.all { permission ->
                ContextCompat.checkSelfPermission(context, permission) ==
                        PackageManager.PERMISSION_GRANTED
            }
            _isAllPermissionsGranted.value = allGranted
        }
    }
}