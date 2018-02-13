package com.teaml.iq.volunteer.ui.base

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
interface BaseSignInSignOutMvpView : FragmentMvpView {

    fun openSignInActivity()

    fun setupViewWithSignInStatus()

    fun setupViewWithSignOutStatus()
}