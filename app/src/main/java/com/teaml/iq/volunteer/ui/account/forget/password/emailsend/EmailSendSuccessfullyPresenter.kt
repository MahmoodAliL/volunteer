package com.teaml.iq.volunteer.ui.account.forget.password.emailsend

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 06/02/2018.
 */
class EmailSendSuccessfullyPresenter<V : EmailSendSuccessfullyMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , EmailSendMvpPresenter<V> {

    override fun onBackClick() {
        mvpView?.showLoginFragment()
    }
}