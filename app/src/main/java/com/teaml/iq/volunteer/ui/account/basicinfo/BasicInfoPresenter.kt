package com.teaml.iq.volunteer.ui.account.basicinfo

import com.google.firebase.auth.UserInfo
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.User
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 06/02/2018.
 */
class BasicInfoPresenter<V : BasicInfoMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , BasicInfoMvpPresenter<V> {


    override fun onDoneClick(name: String, gender: DataManager.UserGender, birthOfDate: Long) {
        mvpView?.let { view ->

            when {
                !view.isNetworkConnection() -> view.onError(R.string.connection_error)

                name.isEmpty() -> view.onError(R.string.empty_name)


                else -> {

                    view.getBaseActivity()?.let { activity ->
                        view.hideKeyboard()
                        view.showLoading()

                        val basicUserInfo = hashMapOf(
                                User.USER_NAME to name,
                                User.GENDER to gender.type,
                                User.BIRTH_OF_DAY to birthOfDate
                        )
                        dataManager.saveBasicUserInfo(basicUserInfo).addOnCompleteListener(activity) { task ->

                            if (mvpView == null)
                                return@addOnCompleteListener

                            if(task.isSuccessful) {
                                view.showMessage("isSuccessful")
                            } else {
                                view.onError("${task.exception?.message}")
                            }


                            view.hideLoading()
                        }
                    }
                }
            }


        }
    }


}