package com.dt5gen.expectedcall.service

import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.IBinder
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.util.Log

class CallService : Service() {

    private lateinit var telephonyManager: TelephonyManager
    private lateinit var notificationManager: NotificationManager

    private val telephonyCallback = object : TelephonyCallback(),
        TelephonyCallback.CallStateListener {
        override fun onCallStateChanged(state: Int) {
            if (state == TelephonyManager.CALL_STATE_RINGING) {
                Log.d("CallService", "Входящий вызов")
                // Обработка звонка
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        telephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        telephonyManager.registerTelephonyCallback(mainExecutor, telephonyCallback)
    }

    private fun disableDoNotDisturb() {
        if (notificationManager.isNotificationPolicyAccessGranted) {
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL)
            Log.d("DND", "Режим 'Не беспокоить' отключён")
        } else {
            Log.d("DND", "Нет разрешения на изменение режима DND")
        }
    }

    private fun enableDoNotDisturb() {
        if (notificationManager.isNotificationPolicyAccessGranted) {
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY)
            Log.d("DND", "Режим 'Не беспокоить' включён")
        }
    }

    private fun setRingerMode(mode: Int) {
        val audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.ringerMode = mode
    }

    override fun onDestroy() {
        super.onDestroy()
        telephonyManager.unregisterTelephonyCallback(telephonyCallback)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}