package com.teaml.iq.volunteer.ui.main.myaccount

import com.teaml.iq.volunteer.ui.base.BaseSignInSignOutMvpPresenter

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
interface MyAccountMvpPresenter<V : MyAccountMvpView> : BaseSignInSignOutMvpPresenter<V> {

    fun fetchProfileInfo()

    fun onMyProfileClick()

    fun onResume()
}