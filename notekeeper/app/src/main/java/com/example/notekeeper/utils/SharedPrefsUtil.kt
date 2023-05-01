package com.example.notekeeper.utils

import android.content.Context

class SharedPrefsUtil(context: Context, preferenceName: String) {
    private val sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE)

    fun <T> get(keyValuePair: Pair<String, Class<T>>, defaultValue: Any): Any? {
            val (key, keyType) = keyValuePair
        return when (keyType) {
            String::class.java -> sharedPreferences.getString(key, defaultValue as? String) as? T
            Int::class.java -> sharedPreferences.getInt(key, defaultValue as? Int ?: 0) as? T
            Long::class.java -> sharedPreferences.getLong(key, defaultValue as? Long ?: 0L) as? T
            Float::class.java -> sharedPreferences.getFloat(key, defaultValue as? Float ?: 0.0f) as? T
            Boolean::class.java -> sharedPreferences.getBoolean(key, defaultValue as? Boolean ?: false) as? T
            Set::class.java -> sharedPreferences.getStringSet(key, defaultValue as? Set<String> ?: setOf()) as? T
            else -> defaultValue
        }
    }

    fun <T> set(keyValuePair: Pair<String, Class<T>>, value: Any) {
        val (key, keyType) = keyValuePair
        when (keyType) {
            String::class.java  -> sharedPreferences.edit().putString(key,    value as String ?:      ""      ).apply()
            Int::class.java     -> sharedPreferences.edit().putInt(key,       value as Int ?:         0       ).apply()
            Long::class.java    -> sharedPreferences.edit().putLong(key,      value as Long ?:        0L      ).apply()
            Float::class.java   -> sharedPreferences.edit().putFloat(key,     value as Float ?:       0.0f    ).apply()
            Boolean::class.java -> sharedPreferences.edit().putBoolean(key,   value as Boolean ?:     false   ).apply()
            Set::class.java     -> sharedPreferences.edit().putStringSet(key, value as Set<String> ?: setOf() ).apply()
            else                -> throw IllegalArgumentException("Unsupported data type")
        }
    }
}
