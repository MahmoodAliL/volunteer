package com.teaml.iq.volunteer.ui.account.forget.password.emailsend

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 06/02/2018.
 */
interface EmailSendSuccessfullyMvpPresenter<V : EmailSendSuccessfullyMvpView> : MvpPresenter<V> {

    fun onBackClick()
}