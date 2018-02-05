package com.teaml.iq.volunteer.ui.account.signin

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/1/2018.
 */
interface SignInMvpPresenter<V : SignInMvpView> : MvpPresenter<V> {
    fun onBtnSignInClicked(email: String, password: String)

    fun onBtnSignUpClicked()

}