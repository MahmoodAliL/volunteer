package com.teaml.iq.volunteer.data

import android.content.Context
import com.teaml.iq.volunteer.data.pref.PreferenceHelper
import com.teaml.iq.volunteer.di.annotation.ApplicationContext
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 24/01/2018.
 */
class AppDataManager @Inject constructor(@ApplicationContext context: Context,
                                         val  preferenceHelper: PreferenceHelper) : DataManager {

    /**
     * SharedPreferences
     */
    override fun setFirstStart(value: Boolean) = preferenceHelper.setFirstStart(value)

    override fun isFirstStart(): Boolean = preferenceHelper.isFirstStart()

    override fun hasBaseProfileInfo(): Boolean {
        return false
    }




}