package com.teaml.iq.volunteer.ui.group

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
class GroupsPresenter<V : GroupsMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), GroupsMvpPresenter<V> {


    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        mvpView.showGroupDetailFragment()
    }

}