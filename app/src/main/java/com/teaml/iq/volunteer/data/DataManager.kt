package com.teaml.iq.volunteer.data

import com.teaml.iq.volunteer.data.firebase.FirebaseHelper
import com.teaml.iq.volunteer.data.pref.PreferenceHelper

/**
 * Created by Mahmood Ali on 24/01/2018.
 */
interface DataManager : PreferenceHelper, FirebaseHelper {

    enum class LoggedInMode(val type: Int) {

        LOGGED_IN_WITH_EMAIL(0),
        LOGGED_OUT(1);

    }

    enum class UserGender(val type: Int) {
        MALE(0),
        FEMALE(1)
    }




    fun hasBaseProfileInfo(): Boolean
}