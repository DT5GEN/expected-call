package com.dt5gen.expectedcall.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dt5gen.expectedcall.utils.PermissionHelper
import com.dt5gen.expectedcall.viewModels.PermissionViewModel

@Composable
fun PermissionScreen(permissionViewModel: PermissionViewModel = viewModel()) {
    // Правильное использование collectAsState для StateFlow
    val isPermissionGranted by permissionViewModel.isPermissionGranted.collectAsState()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        permissionViewModel.checkPermission()
    }

    LaunchedEffect(Unit) {
        // Проверяем разрешения при запуске экрана
        permissionViewModel.checkPermission()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isPermissionGranted) {
            Text("Разрешение предоставлено")
        } else {
            Text("Разрешение не предоставлено")
            Spacer(modifier = Modifier.height(8.dp))
            Button(onClick = {
                PermissionHelper.requestPermission(launcher, PermissionHelper.CONTACTS_PERMISSION)
            }) {
                Text("Запросить разрешение")
            }
        }
    }
}