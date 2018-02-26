package com.teaml.iq.volunteer.ui.profile

import com.teaml.iq.volunteer.ui.base.MvpView

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
interface ProfileMvpView : MvpView {

    fun showProfileInfoFragment(uid: String)

    fun updateToolbarToProfileInfo()

    fun updateToolbarToEditProfile()

}