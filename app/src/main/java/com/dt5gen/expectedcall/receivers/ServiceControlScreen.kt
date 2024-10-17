package com.dt5gen.expectedcall.receivers

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dt5gen.expectedcall.service.CallService


@Composable
fun ServiceControlScreen(context: Context) {
    var isServiceRunning by remember { mutableStateOf(false) }
    var dndStatus by remember { mutableStateOf("Неизвестно") }

    // Вызов функции для проверки состояния DND при запуске
    LaunchedEffect(Unit) {
        dndStatus = getDndStatus(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = if (isServiceRunning) "Сервис запущен" else "Сервис остановлен")

        Spacer(modifier = Modifier.height(8.dp))

        Text(text = "Статус DND: $dndStatus")

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (!isServiceRunning) {
                startCallService(context)
            } else {
                stopCallService(context)
            }
            isServiceRunning = !isServiceRunning
        }) {
            Text(if (isServiceRunning) "Остановить сервис" else "Запустить сервис")
        }
    }


}

// Функция для получения текущего статуса DND
fun getDndStatus(context: Context): String {
    val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    return if (notificationManager.isNotificationPolicyAccessGranted) {
        when (notificationManager.currentInterruptionFilter) {
            NotificationManager.INTERRUPTION_FILTER_ALL -> "Отключен"
            NotificationManager.INTERRUPTION_FILTER_PRIORITY -> "Только важные"
            NotificationManager.INTERRUPTION_FILTER_NONE -> "Полное безмолвие"
            NotificationManager.INTERRUPTION_FILTER_ALARMS -> "Только будильники"
            else -> "Неизвестно"
        }
    } else {
        "Нет доступа"
    }
}

fun startCallService(context: Context) {
    val intent = Intent(context, CallService::class.java)
    context.startForegroundService(intent)
}

fun stopCallService(context: Context) {
    val intent = Intent(context, CallService::class.java)
    context.stopService(intent)
}