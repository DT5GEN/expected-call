package com.dt5gen.expectedcall.service


import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class CallService : Service() {
    override fun onCreate() {
        super.onCreate()
        Log.d("CallService", "Service started")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("CallService", "Service is running")
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null
}