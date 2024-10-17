package com.dt5gen.expectedcall.utils

import android.content.Context
import android.media.AudioManager
import com.dt5gen.expectedcall.model.Contact
import com.dt5gen.expectedcall.viewModels.ContactViewModel

fun setRingerMode(context: Context, mode: Int) {
    val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    audioManager.ringerMode = mode
}

fun switchToLoudIfNeeded(
    context: Context,
    incomingNumber: String,
    selectedContacts: List<Contact>
): ContactViewModel {
    val contactNumbers = selectedContacts.map { it.phoneNumber }
    if (incomingNumber in contactNumbers) {
        setRingerMode(context, AudioManager.RINGER_MODE_NORMAL)
    }
}