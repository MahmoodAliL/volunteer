package com.teaml.iq.volunteer.data.pref

import android.content.Context
import android.content.SharedPreferences
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.di.annotation.ApplicationContext
import com.teaml.iq.volunteer.di.annotation.PreferenceName
import com.teaml.iq.volunteer.utils.setValue
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 31/01/2018.
 */
class AppPreferenceHelper @Inject constructor(@ApplicationContext context: Context,
                    @PreferenceName prefFileName: String) : PreferenceHelper {

    private val prefs: SharedPreferences =
            context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_KEY_FIRST_START = "PREF_KEY_FIRST_START"
        private const val PREF_KEY_LOGGED_IN_MODE = "PREF_KEY_LOGGED_IN_MODE"
        private const val PREF_KEY_HAS_BASIC_PROFILE_INFO = "PREF_KEY_HAS_BASIC_PROFILE_INFO"
        private const val PREF_KEY_HAS_GROUP = "PREF_KEY_HAS_GROUP"
    }

    override fun isFirstStart(): Boolean = prefs.getBoolean(PREF_KEY_FIRST_START, true)

    override fun setFirstStart(value: Boolean) {
        prefs.setValue(PREF_KEY_FIRST_START, value)
    }

    // user info
    override fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode) =
            prefs.setValue(PREF_KEY_LOGGED_IN_MODE, mode.type)

    override fun getCurrentUserLoggedInMode(): Int =
            prefs.getInt(PREF_KEY_LOGGED_IN_MODE, DataManager.LoggedInMode.LOGGED_OUT.type)

    override fun hasGroup(): Boolean {
        return prefs.getBoolean(PREF_KEY_HAS_GROUP, false)
    }

    override fun setHasGroup(hasGroup: Boolean) {
        prefs.setValue(PREF_KEY_HAS_GROUP, hasGroup)
    }

    override fun hasBasicProfileInfo(): Boolean = prefs.getBoolean(PREF_KEY_HAS_BASIC_PROFILE_INFO, false)

    override fun setHasBasicProfileInfo(value: Boolean) {
        prefs.setValue(PREF_KEY_HAS_BASIC_PROFILE_INFO, value)
    }

}