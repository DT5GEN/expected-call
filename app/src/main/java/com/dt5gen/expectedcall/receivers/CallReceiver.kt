package com.dt5gen.expectedcall.receivers


import android.content.Context
import android.media.AudioManager
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import androidx.annotation.RequiresPermission
import com.dt5gen.expectedcall.utils.ContactManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CallReceiver(context: Context) {

    private val telephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        registerTelephonyCallback()
    }

    // Регистрация TelephonyCallback вместо устаревшего PhoneStateListener
    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    private fun registerTelephonyCallback() {
        telephonyManager.registerTelephonyCallback(
            context.mainExecutor,
            object : TelephonyCallback.CallStateListener {
                override fun onCallStateChanged(state: Int) {
                    if (state == TelephonyManager.CALL_STATE_RINGING) {
                        coroutineScope.launch {
                            handleIncomingCall()
                        }
                    }
                }
            })
    }

    // Обработка входящего вызова
    private suspend fun handleIncomingCall() {
        val selectedContacts = ContactManager.getContactsFromPreferences(context)
        val incomingNumber = getLastIncomingNumber()  // Эмуляция получения номера
        if (incomingNumber in selectedContacts) {
            switchToLoudMode(context)
        }
    }

    // Получение последнего входящего номера (эмуляция)
    private fun getLastIncomingNumber(): String {
        // В реальном коде используйте журнал вызовов или другой механизм
        return "+1234567890"
    }

    // Включение громкого режима
    private fun switchToLoudMode(context: Context) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
    }
}