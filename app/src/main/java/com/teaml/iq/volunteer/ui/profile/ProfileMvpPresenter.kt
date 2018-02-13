package com.teaml.iq.volunteer.ui.profile

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
interface ProfileMvpPresenter<V : ProfileMvpView> : MvpPresenter<V> {

    fun loadUserInfo(uid: String?)

    fun onActionEditClick()

    fun canEditProfile(): Boolean
}