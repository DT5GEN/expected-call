package com.dt5gen.expectedcall.utiils

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.dt5gen.expectedcall.model.Contact

@Composable
fun ContactListScreen(
    contacts: List<Contact>,
    onContactSelected: (Contact) -> Unit
) {
    LazyColumn {
        items(contacts) { contact ->
            ContactItem(contact, onContactSelected)
        }
    }
}

@Composable
fun ContactItem(contact: Contact, onClick: (Contact) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick(contact) }
    ) {
        Text(
            text = contact.name,
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.weight(1f)
        )
        IconButton(onClick = { onClick(contact) }) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }
    }
}