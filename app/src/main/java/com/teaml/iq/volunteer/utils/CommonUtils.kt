package com.teaml.iq.volunteer.utils

import android.content.Context
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import java.util.regex.Pattern

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
object CommonUtils {

    private const val EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

    fun isVaildEmail(email: String): Boolean {
        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun intGenderToString(type: Int, context: Context) =
            when (type) {
                DataManager.UserGender.MALE.type -> context.getString(R.string.male)

                DataManager.UserGender.FEMALE.type -> context.getString(R.string.female)

                else -> context.getString(R.string.any)
            }

}