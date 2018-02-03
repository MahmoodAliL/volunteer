package com.teaml.iq.volunteer.ui.account.signin

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/1/2018.
 */
interface SigninMvpPresenter<V : SigninMvpView> : MvpPresenter<V> {

    fun onSignInClick(email: String, password: String)
}