package com.dt5gen.expectedcall.ui.screens

import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
            ContactItemWithRingtone(contact) { selectedContact, ringtoneUri ->
                onContactSelected(selectedContact)  // Можем здесь также сохранить URI, если нужно
            }
        }
    }
}

@Composable
fun ContactItemWithRingtone(
    contact: Contact,
    onRingtoneSelected: (Contact, Uri) -> Unit
) {
    var selectedRingtone by remember { mutableStateOf<Uri?>(null) }
    val launchPicker = pickRingtone { uri ->
        selectedRingtone = uri
        if (uri != null) {
            onRingtoneSelected(contact, uri)
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { launchPicker() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = contact.name,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { launchPicker() }) {
            Icon(Icons.Default.PlayArrow, contentDescription = "Select Ringtone")
        }
    }
}


@Composable
fun pickRingtone(
    onRingtonePicked: (Uri?) -> Unit
): () -> Unit {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.getParcelableExtra<Uri>(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
        onRingtonePicked(uri)
    }

    return {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_RINGTONE)
            putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, "Select Ringtone")
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
        }
        launcher.launch(intent)
    }
}