package com.teaml.iq.volunteer.ui.main

import com.teaml.iq.volunteer.ui.base.MvpView

/**
 * Created by ali on 2/3/2018.
 */
interface MainMvpView : MvpView {


    fun addFragmentWithSignInStatus()

    fun addFragmentWithSignOutStatus()
/*

    fun showHomeFragment()

    fun showGroupFragment()

    fun showMyActivityFragment()

    fun showMyAccountFragment()

*/

}