package com.teaml.iq.volunteer.ui.intro

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by ali on 2/1/2018.
 */
class IntroPresenter<V : IntroMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , IntroMvpPresenter<V> {


}