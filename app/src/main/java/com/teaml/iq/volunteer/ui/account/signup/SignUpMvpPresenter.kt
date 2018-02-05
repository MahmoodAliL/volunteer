package com.teaml.iq.volunteer.ui.account.signup

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/1/2018.
 */
interface SignUpMvpPresenter<V : SignUpMvpView> : MvpPresenter<V> {
    fun onSignUpClick(email: String, password: String, confirmPassword: String)

    fun onSignInClick()
}