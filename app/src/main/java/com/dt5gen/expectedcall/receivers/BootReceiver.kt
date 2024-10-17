package com.dt5gen.expectedcall.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.dt5gen.expectedcall.service.CallService

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            Log.d("BootReceiver", "Device rebooted. BootReceiver triggered.")

            // Здесь можem запустить фоновый сервис, если требуется
            val callServiceIntent = Intent(context, CallService::class.java)
            context.startForegroundService(callServiceIntent)
        }
    }
}