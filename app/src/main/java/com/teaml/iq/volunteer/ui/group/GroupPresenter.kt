package com.teaml.iq.volunteer.ui.group

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
class GroupPresenter<V : GroupMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), GroupMvpPresenter<V> {


    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        mvpView.showGroupDetailFragment()
    }

    override fun OnBackStackChangedListener(backStackEntryCount: Int) {
        if (backStackEntryCount == 0 ) {
            mvpView?.updateToolbarToGroupDetail()
        } else {
            mvpView?.updateToolbarToGroupCampaigns()
        }
    }
}