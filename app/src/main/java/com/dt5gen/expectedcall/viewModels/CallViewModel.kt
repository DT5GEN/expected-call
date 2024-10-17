package com.dt5gen.expectedcall.viewModels

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.telephony.PhoneStateListener
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dt5gen.expectedcall.utils.ContactManager
import kotlinx.coroutines.launch

class CallViewModel(application: Application) : AndroidViewModel(application) {

    private val context: Context = getApplication<Application>().applicationContext
    private val telephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    private var isListenerRegistered = false

    init {
        // Проверяем разрешения и регистрируем слушателя, если возможно
        if (hasPhonePermissions()) {
            registerCallListener()
        }
    }

    // Проверка разрешений на READ_CALL_LOG и READ_PHONE_STATE
    private fun hasPhonePermissions(): Boolean {
        val readCallLogGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_CALL_LOG
        ) == PackageManager.PERMISSION_GRANTED

        val readPhoneStateGranted = ContextCompat.checkSelfPermission(
            context, Manifest.permission.READ_PHONE_STATE
        ) == PackageManager.PERMISSION_GRANTED

        return readCallLogGranted && readPhoneStateGranted
    }

    // Регистрация слушателя вызовов
    private fun registerCallListener() {
        telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_CALL_STATE)
        isListenerRegistered = true
    }

    // Слушатель состояния вызовов
    private val callStateListener = object : PhoneStateListener() {
        override fun onCallStateChanged(state: Int, phoneNumber: String?) {
            if (state == TelephonyManager.CALL_STATE_RINGING && phoneNumber != null) {
                handleIncomingCall(phoneNumber)
            }
        }
    }

    // Обработка входящего вызова
    private fun handleIncomingCall(phoneNumber: String) {
        viewModelScope.launch {
            val selectedContacts = ContactManager.getContactsFromPreferences(context)
            if (phoneNumber in selectedContacts) {
                switchToLoudMode()
            }
        }
    }

    // Включение громкого режима
    private fun switchToLoudMode() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
    }

    override fun onCleared() {
        super.onCleared()
        // Остановка слушателя только если он был зарегистрирован
        if (isListenerRegistered) {
            telephonyManager.listen(callStateListener, PhoneStateListener.LISTEN_NONE)
        }
    }
}