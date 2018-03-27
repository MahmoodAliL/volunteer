package com.teaml.iq.volunteer.ui.profile.password

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 3/25/2018.
 */
interface ChangePasswordMvpPresenter<V: ChangePasswordMvpView>: MvpPresenter<V> {

    fun onDoneClick(oldPassword: String, newPassword: String, confirmPassword: String)

}