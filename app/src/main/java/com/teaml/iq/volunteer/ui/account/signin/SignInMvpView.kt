package com.teaml.iq.volunteer.ui.account.signin

import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by ali on 2/1/2018.
 */
interface SignInMvpView : FragmentMvpView {

    fun showSignUpFragment()

    fun showForgetPasswordFragment()

    fun showBasicInfoFragment()

    fun openMainActivity()

}