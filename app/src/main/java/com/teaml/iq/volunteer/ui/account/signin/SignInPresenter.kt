package com.teaml.iq.volunteer.ui.account.signin

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.CommonUtils
import javax.inject.Inject

/**
 * Created by ali on 2/1/2018.
 */
class SignInPresenter<V : SignInMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), SignInMvpPresenter<V> {


    override fun onBtnSignInClicked(email: String, password: String) {
        // validate email and password

        mvpView?.let { view ->

            if (!view.isNetworkConnection()) {
                view.onError(R.string.connection_error)
                return
            }

            // validate input
            if (email.isEmpty()) {
                view.onError(R.string.empty_email)
                return
            }

            if (!CommonUtils.isVaildEmail(email)) {
                view.onError(R.string.invalid_email)
                return
            }

            if (password.isEmpty()) {
                view.onError(R.string.empty_password)
                return
            }

            view.hideKeyboard()
            view.showLoading(R.string.please_wait)

            view.getBaseActivity()?.let { activity ->

                dataManager.signWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity) { task ->
                            // some time user exit from app and listener fire so any interaction with ui is
                            // produce error for that reason we check if mvpView is null or not
                            if (mvpView == null)
                                return@addOnCompleteListener

                            view.hideLoading()
                            if (task.isSuccessful) {
                                view.showMessage("Sign In Successfully")
                            } else {
                                view.onError("${task.exception?.message}")
                            }
                        }

            }
            }


    }


    override fun onBtnSignUpClicked() {
        mvpView?.showSignUpFragment()
    }
}