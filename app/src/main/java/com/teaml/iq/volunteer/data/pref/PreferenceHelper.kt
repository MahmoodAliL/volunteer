package com.teaml.iq.volunteer.data.pref

import com.teaml.iq.volunteer.data.DataManager

/**
 * Created by Mahmood Ali on 31/01/2018.
 */
interface PreferenceHelper {

    fun setFirstStart(value: Boolean)

    fun isFirstStart(): Boolean


    // user info

    fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode)

    fun getCurrentUserLoggedInMode(): Int

    fun setHasBasicProfileInfo(value: Boolean)

    fun setHasGroup(hasGroup: Boolean)

    fun hasGroup(): Boolean

    fun hasBasicProfileInfo(): Boolean

}