package com.teaml.iq.volunteer.utils

import android.content.SharedPreferences
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*

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

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}

// Add or replace fragment only without add to back stack
fun AppCompatActivity.replaceFragment(frameId: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.inTransaction { replace(frameId, fragment, tag) }
}

fun FragmentActivity.replaceFragment(frameId: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.inTransaction { replace(frameId, fragment, tag);  }
}

fun AppCompatActivity.addFragment(frameId: Int, fragment: Fragment, tag: String?) {
    supportFragmentManager.inTransaction { add(frameId, fragment, tag)  }
}

fun FragmentActivity.addFragment(frameId: Int, fragment: Fragment, tag: String?) {
    supportFragmentManager.inTransaction { add(frameId, fragment, tag)  }
}

// Add or replace fragment with add to back stack
fun AppCompatActivity.addFragmentAndAddToBackStack(frameId: Int, fragment: Fragment, tag: String?) {
    supportFragmentManager.inTransaction { add(frameId, fragment, tag); addToBackStack(tag) }
}

fun AppCompatActivity.replaceFragmentAndAddToBackStack(frameId: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.inTransaction { replace(frameId, fragment, tag); addToBackStack(tag) }
}

fun FragmentActivity.addFragmentAndAddToBackStack(frameId: Int, fragment: Fragment, tag: String?) {
    supportFragmentManager.inTransaction { add(frameId, fragment, tag); addToBackStack(tag) }
}

fun FragmentActivity.replaceFragmentAndAddToBackStack(frameId: Int, fragment: Fragment, tag: String? = null) {
    supportFragmentManager.inTransaction { replace(frameId, fragment, tag); addToBackStack(tag) }
}


// view utils
val View.visible: Unit
    get() {
        visibility = View.VISIBLE
    }
val View.invisible: Unit
    get() {
        visibility = View.INVISIBLE
    }

val View.gone: Unit
    get() { visibility = View.GONE}

// Date utils


fun Date.toDateString(): String {
    val format = SimpleDateFormat(AppConstants.DATE_FORMAT, Locale.ENGLISH)
    return format.format(this)
}

fun Date.toTimeString(): String {
    val format = SimpleDateFormat(AppConstants.TIME_FORMAT, Locale.ENGLISH)
    return format.format(this)
}

fun Date.toTimestamp(): String {
    val format = SimpleDateFormat(AppConstants.TIMESTAMP_FORMAT, Locale.ENGLISH)
    return format.format(this)
}


// ui utils

fun TextView.clearText() {
    this.text = ""
}

