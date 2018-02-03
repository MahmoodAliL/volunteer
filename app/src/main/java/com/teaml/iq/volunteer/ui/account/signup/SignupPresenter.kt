package com.teaml.iq.volunteer.ui.account.signup

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by ali on 2/1/2018.
 */
class SignupPresenter<V : SignupMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , SignupMvpPresenter<V>  {



}