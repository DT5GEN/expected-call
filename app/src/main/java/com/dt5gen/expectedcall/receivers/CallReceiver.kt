package com.dt5gen.expectedcall.receivers

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.telephony.TelephonyCallback
import android.telephony.TelephonyManager
import android.util.Log
import androidx.annotation.RequiresPermission
import com.dt5gen.expectedcall.utils.ContactManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("MissingPermission")
class CallReceiver(private val context: Context) {

    private val telephonyManager =
        context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    init {
        if (hasPermission()) {
            registerTelephonyCallback()
        } else {
            Log.e("CallReceiver", "READ_PHONE_STATE permission not granted")
        }
    }

    private fun hasPermission(): Boolean {
        return context.checkSelfPermission(android.Manifest.permission.READ_PHONE_STATE) ==
                PackageManager.PERMISSION_GRANTED
    }

    @RequiresPermission(android.Manifest.permission.READ_PHONE_STATE)
    private fun registerTelephonyCallback() {
        try {
            telephonyManager.registerTelephonyCallback(
                context.mainExecutor,
                object : TelephonyCallback(), TelephonyCallback.CallStateListener {
                    override fun onCallStateChanged(state: Int) {
                        if (state == TelephonyManager.CALL_STATE_RINGING) {
                            coroutineScope.launch {
                                handleIncomingCall()
                            }
                        }
                    }
                }
            )
        } catch (e: SecurityException) {
            Log.e("CallReceiver", "Failed to register callback: ${e.message}")
        }
    }

    private fun handleIncomingCall() {
        val selectedContacts = ContactManager.getContactsFromPreferences(context)
        val incomingNumber = getLastIncomingNumber()
        if (incomingNumber in selectedContacts) {
            switchToLoudMode()
        }
    }

    private fun getLastIncomingNumber(): String {
        // Здесь эмуляция получения номера. Замените на реальный источник, если потребуется.
        return "+1234567890"
    }

    private fun switchToLoudMode() {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
    }
}
