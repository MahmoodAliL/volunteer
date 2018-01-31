package com.teaml.iq.volunteer.ui.splash

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 01/02/2018.
 */
class SplashPresenter<V : SplashMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , SplashMvpPresenter<V> {


}