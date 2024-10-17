package com.dt5gen.expectedcall.utils

import android.content.Context
import android.media.AudioManager

object LoudManager {
    fun switchToLoudMode(context: Context) {
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
        audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
    }
}