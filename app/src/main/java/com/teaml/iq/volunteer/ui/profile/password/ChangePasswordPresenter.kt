package com.teaml.iq.volunteer.ui.profile.password

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
                view.showMessage("msg old")
                return
            }
            if(newPassword.isEmpty()) {
                view.showMessage("msg new")
                return
            }
            if (oldPassword.length < 6) {
                view.showMessage("msg length")
                return
            }
            if(newPassword.length <6) {
                view.showMessage("msg length")
                return
            }
            if(confirmPassword.isEmpty()) {
                view.showMessage("msg confirm")
                return
            }
            if(newPassword != confirmPassword){
                view.showMessage("msg not equal")
                return
            }

            dataManager.checkOldPassword(oldPassword).addOnCompleteListener { task ->
                view.showLoading()
                view.hideKeyboard()
                if(task.isSuccessful)
                    dataManager.onUpdatePassword(newPassword).addOnCompleteListener {
                        if(it.isSuccessful) {
                            view.hideLoading()
                            view.showMessage("Password updated")
                            view.showProfileInfoFragment()

                        }else{
                            view.hideLoading()
                            view.onError("Error password not updated")
                        }
                    }
                else {
                    view.hideLoading()
                    view.onError("Error auth failed")
                }
            }
        }
    }
}