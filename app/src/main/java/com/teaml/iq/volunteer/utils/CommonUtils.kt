package com.teaml.iq.volunteer.utils

import android.content.Context
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import java.util.*
import java.util.concurrent.TimeUnit
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

    fun dateFrom(year: Int, month: Int, dayOfMonth: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, dayOfMonth, 0, 0)
        return calendar.time
    }

    fun getHumanReadableElapseTime(uploadDate: Date, context: Context): String {

        val calendarUploadDate = Calendar.getInstance()
        calendarUploadDate.time = uploadDate

        val differentTime = (System.currentTimeMillis() - calendarUploadDate.timeInMillis)

        val seconds = TimeUnit.MILLISECONDS.toSeconds(differentTime)

        if (seconds <= 59) {
            return context.getString(R.string.less_then_minute)
        }

        val minutes = TimeUnit.MILLISECONDS.toMinutes(differentTime)

        if (minutes <= 59) {
            return context.getString(R.string.since_x_minutes, minutes)
        }

        val hours = TimeUnit.MILLISECONDS.toHours(differentTime)

        if (hours <= 23) {
            return context.getString(R.string.since_x_hours, hours)
        }

        val days = TimeUnit.MILLISECONDS.toDays(differentTime)
        if (days <= 6) {
            return context.getString(R.string.since_x_days, days)
        }

        val weeks = days / 7
        if (weeks <= 4) {
            return context.getString(R.string.since_x_weeks, weeks)
        }


        return uploadDate.toDateString()
    }
}