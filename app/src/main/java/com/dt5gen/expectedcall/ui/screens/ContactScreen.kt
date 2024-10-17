package com.dt5gen.expectedcall.ui.screens

import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.dt5gen.expectedcall.model.Contact

@Composable
fun ContactListScreen(
    contacts: List<Contact>,
    onContactSelected: (Contact) -> Unit
) {
    LazyColumn {
        items(contacts) { contact ->
            ContactItemWithRingtone(contact, onContactSelected)
        }
    }
}

@Composable
fun ContactItemWithRingtone(
    contact: Contact,
    onRingtoneSelected: (Contact, Uri) -> Unit
) {
    var selectedRingtone by remember { mutableStateOf<Uri?>(null) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable {
                pickRingtone(LocalContext.current) { uri ->
                    selectedRingtone = uri
                    onRingtoneSelected(contact, uri)
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = contact.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = {
            pickRingtone(LocalContext.current) { uri ->
                selectedRingtone = uri
                if (uri != null) {
                    onRingtoneSelected(contact, uri)
                }
            }
        }) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Select Ringtone")
        }
    }
}

fun pickRingtone(context: Context, onRingtonePicked: (Uri?) -> Unit) {
    val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
        putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE)
        putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone")
        putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
    }

    val activity = context as? ComponentActivity
    activity?.startActivityForResult(intent, 999)
}