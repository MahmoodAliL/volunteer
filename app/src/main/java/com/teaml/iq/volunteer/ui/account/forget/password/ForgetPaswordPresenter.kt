package com.teaml.iq.volunteer.ui.account.forget.password

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 05/02/2018.
 */
class ForgetPaswordPresenter<V : ForgetPasswordMvpView> @Inject constructor(dataManager: DataManager)
    :  BasePresenter<V>(dataManager), ForgetPasswordMvpPresenter<V> {


}