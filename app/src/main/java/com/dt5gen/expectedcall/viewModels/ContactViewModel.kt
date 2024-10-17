package com.dt5gen.expectedcall.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dt5gen.expectedcall.model.Contact
import com.dt5gen.expectedcall.utils.ContactManager
import com.dt5gen.expectedcall.utils.LoudManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel : ViewModel() {

    private val _selectedContact = MutableStateFlow<Contact?>(null)
    val selectedContact: StateFlow<Contact?> = _selectedContact

    private val _contacts = MutableStateFlow<List<Contact>>(emptyList())
    val contacts: StateFlow<List<Contact>> = _contacts

    fun loadContacts(context: Context) {
        viewModelScope.launch {
            _contacts.value = ContactManager.getAllContacts(context)
        }
    }

    fun selectContact(contact: Contact, context: Context) {
        _selectedContact.value = contact
        LoudManager.switchToLoudMode(context)
    }
}