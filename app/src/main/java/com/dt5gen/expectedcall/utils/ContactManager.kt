package com.dt5gen.expectedcall.utils


import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ContactManager {

    private const val PREFS_NAME = "contacts_prefs"
    private const val CONTACTS_KEY = "selected_contacts"

    // Сохранение контактов в SharedPreferences
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
}