package com.teaml.iq.volunteer.data

import com.teaml.iq.volunteer.data.pref.PreferenceHelper

/**
 * Created by Mahmood Ali on 24/01/2018.
 */
interface DataManager : PreferenceHelper {

    enum class LoggedInMode(type: Int) {

        LOGGED_IN_WITH_EMAIL(0),
        LOGGED_OUT(1);

        var type: Int = type
            private set

    }

    fun hasBaseProfileInfo(): Boolean
}