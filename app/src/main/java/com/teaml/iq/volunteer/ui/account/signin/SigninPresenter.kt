package com.teaml.iq.volunteer.ui.account.signin

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by ali on 2/1/2018.
 */
class SigninPresenter<V : SigninMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , SigninMvpPresenter<V>{

    override fun onSignInClick(email: String, password: String) {

    }

    fun checkEmail(email: String){

    }
    fun checkPassword(password: String){

    }
}