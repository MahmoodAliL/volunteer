package com.teaml.iq.volunteer.ui.profile.password

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by ali on 3/25/2018.
 */
class ChangePasswordPresenter<V: ChangePasswordMvpView> @Inject constructor(dateManager: DataManager)
    :BasePresenter<V>(dateManager), ChangePasswordMvpPresenter<V> {

    override fun onDoneClick(oldPassword: String, newPassword: String, confirmPassword: String) {
        mvpView?.let {view ->
            if (oldPassword.isEmpty()) {
                view.showMessage(R.string.empty_password)
                return
            }
            if(newPassword.isEmpty()) {
                view.showMessage(R.string.empty_password)
                return
            }
            if (oldPassword.length < 6) {
                view.showMessage(R.string.invalid_password)
                return
            }
            if(newPassword.length <6) {
                view.showMessage(R.string.invalid_password)
                return
            }
            if(confirmPassword.isEmpty()) {
                view.showMessage(R.string.empty_password)
                return
            }
            if(newPassword != confirmPassword){
                view.showMessage(R.string.not_match_password)
                return
            }

            dataManager.checkOldPassword(oldPassword).addOnCompleteListener { task ->
                view.showLoading()
                view.hideKeyboard()
                if(task.isSuccessful)
                    dataManager.onUpdatePassword(newPassword).addOnCompleteListener {
                        if(it.isSuccessful) {
                            view.hideLoading()
                            view.showMessage(R.string.update_password)
                            view.showProfileInfoFragment()

                        }else{
                            view.hideLoading()
                            view.onError(R.string.connection_error)
                        }
                    }
                else {
                    view.hideLoading()
                    view.onError(R.string.current_password_not_correct)
                }
            }
        }
    }
}