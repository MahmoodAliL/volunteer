package com.teaml.iq.volunteer.ui.main.home

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by ali on 2/4/2018.
 */
class HomePresenter<V : HomeMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), HomeMvpPresenter<V>{


}