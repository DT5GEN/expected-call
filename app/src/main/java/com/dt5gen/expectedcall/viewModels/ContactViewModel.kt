package com.dt5gen.expectedcall.viewModels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.dt5gen.expectedcall.model.Contact

class ContactViewModel : ViewModel() {
    private val _selectedContacts = mutableStateListOf<Contact>()
    val selectedContacts: List<Contact> get() = _selectedContacts

    fun addContact(contact: Contact) {
        if (!_selectedContacts.contains(contact)) {
            _selectedContacts.add(contact)
        }
    }

    fun removeContact(contact: Contact) {
        _selectedContacts.remove(contact)
    }
}