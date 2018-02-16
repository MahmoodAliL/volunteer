package com.teaml.iq.volunteer.utils

import android.content.Context
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import java.util.*
import java.util.regex.Pattern

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
object CommonUtils {

    private const val EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    fun isValidEmail(email: String): Boolean {
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun intGenderToString(type: Int, context: Context): String =
            when (type) {
                DataManager.UserGender.MALE.type -> context.getString(R.string.male)

                DataManager.UserGender.FEMALE.type -> context.getString(R.string.female)

                else -> context.getString(R.string.any)
            }

    fun DateFrom(year: Int, month: Int, dayOfMonth: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.set(year, month -1, dayOfMonth, 0, 0)
            return calendar.time
    }
}