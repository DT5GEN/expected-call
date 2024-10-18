
package com.dt5gen.expectedcall.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dt5gen.expectedcall.utils.PermissionHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PermissionViewModel(application: Application) : AndroidViewModel(application) {
    private val _isPermissionGranted = MutableStateFlow(false)
    val isPermissionGranted: StateFlow<Boolean> = _isPermissionGranted

    fun checkPermission() {
        viewModelScope.launch {
            _isPermissionGranted.value = PermissionHelper.hasPermission(
                getApplication(),
                PermissionHelper.CONTACTS_PERMISSION
            )
        }
    }

    fun requestPermission(request: (String) -> Unit) {
        request(PermissionHelper.CONTACTS_PERMISSION)
    }
}