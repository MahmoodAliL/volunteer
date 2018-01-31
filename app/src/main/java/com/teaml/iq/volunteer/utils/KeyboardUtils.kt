package com.teaml.iq.volunteer.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by Mahmood Ali on 31/01/2018.
 */
object KeyboardUtils {

    fun hideSoftInput(activity: Activity) {
        val view = activity.currentFocus ?: View(activity)

        val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showSoftInput(edit: EditText, context: Context) {
        edit.isFocusable = true
        edit.isFocusableInTouchMode = true
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.showSoftInput(edit, 0)
    }

    fun toggleSoftInput(context: Context) {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}