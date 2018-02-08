package com.teaml.iq.volunteer.ui.account.signin

import android.app.Activity
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


    override fun onSignInClick(email: String, password: String) {


        mvpView?.let { view ->

            // validate email and password
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


            view.getBaseActivity()?.let { activity ->

                view.hideKeyboard()
                view.showLoading(R.string.please_wait)


                dataManager.signWithEmailAndPassword(email, password)
                        .addOnCompleteListener(activity) { task ->
                            // some time user exit from app and listener fire so any interaction with ui is
                            // produce error for that reason we check if mvpView is null or not
                            if (mvpView == null)
                                return@addOnCompleteListener

                            if (task.isSuccessful) {
                                dataManager.setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_WITH_EMAIL)
                                checkBasicUserInfo(activity)
                            } else {
                                view.onError("${task.exception?.message}")
                                view.hideLoading()
                            }
                        }

            }
        }


    }


    private fun checkBasicUserInfo(activity: Activity) {

        // check if user has basic profile info if data of document is exist
        // that mean user save has data to firestore
        dataManager.loadProfileInfo()
                .addOnCompleteListener(activity) { task->

                    mvpView?.hideLoading()

                    if (task.isSuccessful) {
                        if (task.result.exists()) {
                            dataManager.setHasBasicProfileInfo(true)
                            mvpView?.openMainActivity()
                        } else {
                            mvpView?.showBasicInfoFragment()
                        }
                    } else {
                        mvpView?.onError("${task.exception?.message}")
                    }


                }

    }

    override fun onForgetPasswordClick() {
        mvpView?.showForgetPasswordFragment()
    }

    override fun onSignUpClick() {
        mvpView?.showSignUpFragment()
    }
}