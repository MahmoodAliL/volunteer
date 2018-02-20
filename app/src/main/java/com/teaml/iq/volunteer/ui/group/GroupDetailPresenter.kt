package com.teaml.iq.volunteer.ui.group

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
class GroupDetailPresenter<V : GroupDetailMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), GroupDetailMvpPresenter<V> {




}