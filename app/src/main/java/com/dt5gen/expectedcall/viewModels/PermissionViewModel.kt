package com.dt5gen.expectedcall.viewModels


import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.compose.runtime.mutableStateOf
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel

class PermissionViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>().applicationContext

    // Состояние разрешения
    var isPermissionGranted = mutableStateOf(false)
        private set

    // Проверка состояния разрешения
    fun checkPermission() {
        val granted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_CALL_LOG
        ) == PackageManager.PERMISSION_GRANTED
        isPermissionGranted.value = granted
    }

    // Запрос разрешения через ViewModel
    fun requestPermission(onRequest: (String) -> Unit) {
        onRequest(Manifest.permission.READ_CALL_LOG)
    }
}