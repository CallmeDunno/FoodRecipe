package com.example.btl_cnpm.utils

import android.content.SharedPreferences
import javax.inject.Inject

class SharedPreferencesManager @Inject constructor(private val sharedPre: SharedPreferences) {

    fun putString(key: String, value: String) {
        sharedPre.edit().apply {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String): String? {
        return sharedPre.getString(key, null)
    }

    fun removeKey(key: String) {
        sharedPre.edit().remove(key).apply()
    }

    fun clear() {
        sharedPre.edit().apply {
            clear()
            apply()
        }
    }

}