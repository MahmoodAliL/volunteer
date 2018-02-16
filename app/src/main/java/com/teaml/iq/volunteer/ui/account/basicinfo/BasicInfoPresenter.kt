package com.teaml.iq.volunteer.ui.account.basicinfo

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.BasicUserInfo
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 06/02/2018.
 */
class BasicInfoPresenter<V : BasicInfoMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), BasicInfoMvpPresenter<V> {


    override fun onDoneClick(name: String, gender: DataManager.UserGender, birthOfDate: Long) {
        mvpView?.let { view ->

            // validate basic user info
            if (!view.isNetworkConnection()) {
                view.onError(R.string.connection_error)
                return
            }

            if (name.isEmpty()) {
                view.onError(R.string.empty_name)
                return
            }

            if (birthOfDate == 0L) {
                view.onError(R.string.empty_birth_of_date)
                return
            }

            // if user does not has uid that mean user need to sign in
            if (dataManager.getFirebaseUserAuthID() == null) {
                view.openSplashActivity()
                return
            }

            view.getBaseActivity()?.let { activity ->

                view.hideKeyboard()
                view.showLoading()

                val basicUserInfo = BasicUserInfo(name, gender, birthOfDate)

                dataManager.saveProfileInfo(basicUserInfo.toMap()).addOnCompleteListener(activity) { task ->

                    if (mvpView == null)
                        return@addOnCompleteListener
                    view.hideLoading()

                    if (task.isSuccessful) {
                        dataManager.setHasBasicProfileInfo(true)
                        view.openMainActivity()
                    } else {
                        view.onError("${task.exception?.message}")
                    }

                }


            }
        }
    }


}