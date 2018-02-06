package com.teaml.iq.volunteer.ui.account.forget.password

import com.teaml.iq.volunteer.ui.base.DialogMvpView
import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by Mahmood Ali on 05/02/2018.
 */
interface ForgetPasswordMvpView : FragmentMvpView {

    fun showLoginFragment()

    fun showEmailSendSuccessfullyFragment()
}