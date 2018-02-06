package com.teaml.iq.volunteer.ui.account.forget.password

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.CommonUtils
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 05/02/2018.
 */
class ForgetPasswordPresenter<V : ForgetPasswordMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), ForgetPasswordMvpPresenter<V> {


    override fun onResetPasswordClick(email: String) {

        mvpView?.let { view ->

            if (!view.isNetworkConnection()) {
                view.onError(R.string.connection_error)
                return
            }

            if (email.isEmpty()) {
                view.onError(R.string.empty_email)
                return
            }

            if (!CommonUtils.isVaildEmail(email)) {
                view.onError(R.string.invalid_email)
                return
            }

            view.getBaseActivity()?.let { activity ->
                view.showLoading()
                view.hideKeyboard()
                dataManager.sendPasswordResetEmail(email).addOnCompleteListener (activity ){ task ->

                    if (mvpView == null)
                        return@addOnCompleteListener

                    if (task.isSuccessful) {
                        view.showEmailSendSuccessfullyFragment()
                    } else {
                        view.onError("${task.exception?.message}")
                    }

                    view.hideLoading()
                }
            }


        }

    }


    override fun onBackClick() {
        mvpView?.showLoginFragment()
    }
}