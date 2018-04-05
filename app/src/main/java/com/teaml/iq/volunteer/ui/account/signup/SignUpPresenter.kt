package com.teaml.iq.volunteer.ui.account.signup

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.CommonUtils
import javax.inject.Inject

/**
 * Created by ali on 2/1/2018.
 */
class SignUpPresenter<V : SignUpMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , SignUpMvpPresenter<V>  {

    override fun onSignUpClick(email: String, password: String, confirmPassword: String) {
        mvpView?.let { view ->

            if (!view.isNetworkConnection()) {
                view.onError(R.string.connection_error)
                return
            }

            if (email.isEmpty()) {
                view.onError(R.string.empty_email)
                return
            }

            if(!CommonUtils.isValidEmail(email)) {
                view.onError(R.string.invalid_email)
                return
            }

            if (password.isEmpty()) {
                view.onError(R.string.empty_password)
                return
            }

            if (password.length < 6) {
                view.onError(R.string.invalid_password)
                return
            }

            if (password != confirmPassword) {
                view.onError(R.string.password_does_not_match)
                return
            }

            view.hideKeyboard()
            view.getBaseActivity()?.let { activity ->
                view.showLoading(R.string.sign_up)
                dataManager.createUserWithEmailAndPassword(email, password).addOnCompleteListener(activity) { task ->

                    if (mvpView == null)
                        return@addOnCompleteListener

                    if (task.isSuccessful) {
                        dataManager.setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_WITH_EMAIL)
                        view.showBasicInfoFragment()
                    } else {
                        view.onError("${task.exception?.message}")
                    }

                    view.hideLoading()
                }
            }

        }
    }


    override fun onSignInClick() {
        mvpView?.showSignInFragment()
    }
}