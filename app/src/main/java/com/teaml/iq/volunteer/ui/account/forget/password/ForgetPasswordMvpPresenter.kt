package com.teaml.iq.volunteer.ui.account.forget.password

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 05/02/2018.
 */
interface ForgetPasswordMvpPresenter<V : ForgetPasswordMvpView> : MvpPresenter<V> {

    fun onResetPasswordClick(email: String)

    fun onBackClick()

}