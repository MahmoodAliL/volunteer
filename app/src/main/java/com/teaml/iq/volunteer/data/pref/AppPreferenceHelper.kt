package com.teaml.iq.volunteer.data.pref

import android.content.Context
import android.content.SharedPreferences
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
    }

    override fun isFirstStart(): Boolean {
        return prefs.getBoolean(PREF_KEY_FIRST_START, true)
    }

    override fun setFirstStart(value: Boolean) {
        prefs.setValue(PREF_KEY_FIRST_START, value)
    }


}