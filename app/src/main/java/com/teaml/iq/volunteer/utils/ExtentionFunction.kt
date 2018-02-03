package com.teaml.iq.volunteer.utils

import android.content.SharedPreferences

/**
 * Created by Mahmood Ali on 02/02/2018.
 */

/**
 * SharedPreferences Extension Function Helper
 * For More: https://medium.com/@krupalshah55/manipulating-shared-prefs-with-kotlin-just-two-lines-of-code-29af62440285
 */

inline fun SharedPreferences.edit(operation: SharedPreferences.Editor.() -> Unit) {
    val edit = this.edit()
    edit.operation()
    edit.apply()
}

fun SharedPreferences.setValue(key: String, value: Any?) {
    when (value) {

        is String? -> edit { putString(key, value) }

        is Boolean -> edit { putBoolean(key, value) }

        is Int -> edit { putInt(key, value) }

        is Float -> edit { putFloat(key, value) }

        is Long -> edit { putLong(key, value) }

        else -> throw UnsupportedOperationException("Not yet Implemented")

    }
}


/* need more study
inline fun<reified T : Any> SharedPreferences.get(key: String, defaultValue: T? = null): T? {
    return when(T::class) {

        String::class -> getString(key, defaultValue  as? String ) as T?

        Boolean::class ->getBoolean(key, defaultValue as? Boolean ?: false) as T?

        Int::class -> getInt(key, defaultValue as? Int ?: -1 ) as T?

        Float::class -> getFloat(key, defaultValue as? Float ?: -1F) as T?

        Long::class -> getLong(key, defaultValue as? Long ?: -1) as T?

        else -> throw UnsupportedOperationException("Not yet Implemented")
    }
}*/


/**
 * Fragment Extension Function
 */
