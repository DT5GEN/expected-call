package com.dt5gen.expectedcall.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dt5gen.expectedcall.model.Contact
import com.dt5gen.expectedcall.viewModels.ContactViewModel

@Composable
fun SelectContactScreen(
    viewModel: ContactViewModel = viewModel(),
    onContactSelected: (Contact) -> Unit
) {
    // Собираем контакты как StateFlow из viewModel
    val contacts by viewModel.contacts.collectAsState()

    // Получаем текущий контекст
    val context = LocalContext.current

    // Загружаем контакты при открытии экрана
    LaunchedEffect(Unit) {
        viewModel.loadContacts(context)
    }

    // UI для отображения контактов или сообщения об отсутствии доступа
    if (contacts.isEmpty()) {
        Text(
            text = "Нет доступа к контактам. Проверьте разрешения.",
            modifier = Modifier.padding(16.dp)
        )
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(contacts) { contact ->
                Text(
                    text = "${contact.name}: ${contact.phoneNumber}",
                    modifier = Modifier
                        .clickable { onContactSelected(contact) }
                        .padding(8.dp)
                )
            }
        }
    }
}