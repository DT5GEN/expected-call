package com.dt5gen.expectedcall.utils


import android.content.ContentResolver
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.provider.ContactsContract
import androidx.core.content.ContextCompat
import com.dt5gen.expectedcall.model.Contact
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ContactManager {


    private const val PREFS_NAME = "contacts_prefs"
    private const val CONTACTS_KEY = "selected_contacts"

    // Сохранение выбранных контактов в SharedPreferences
    fun saveContactsToPreferences(context: Context, contacts: List<String>) {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(contacts)
        editor.putString(CONTACTS_KEY, json)
        editor.apply()
    }

    // Чтение контактов из SharedPreferences
    fun getContactsFromPreferences(context: Context): List<String> {
        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(CONTACTS_KEY, null)
        val type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(json, type) ?: emptyList()
    }


    fun getAllContacts(context: Context): List<Contact> {
        val contacts = mutableListOf<Contact>()

        // Проверяем разрешение на чтение контактов
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return contacts // Возвращаем пустой список, если разрешение не получено
        }

        val contentResolver: ContentResolver = context.contentResolver
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
            ),
            null, null, null
        )

        cursor?.use {
            val nameIndex =
                it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex =
                it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)

            while (it.moveToNext()) {
                val name = it.getString(nameIndex) ?: "Неизвестный контакт"
                val phoneNumber = it.getString(numberIndex) ?: "Без номера"
                contacts.add(Contact(name, phoneNumber))
            }
        }

        return contacts
    }
}