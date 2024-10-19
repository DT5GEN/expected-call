package com.dt5gen.expectedcall.utils

import android.app.NotificationManager
import android.content.Context
import android.media.AudioManager

object LoudManager {
    fun switchToLoudMode(context: Context) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        // Проверяем разрешение
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (notificationManager.isNotificationPolicyAccessGranted) {
            audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        } else {
            // Лог или уведомление о том, что доступ к "Не беспокоить" не предоставлен
            throw SecurityException("Не удалось изменить режим звонка. Требуется разрешение.")
        }
    }
}
