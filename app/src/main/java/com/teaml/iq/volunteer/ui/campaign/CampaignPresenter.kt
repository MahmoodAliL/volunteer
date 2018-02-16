package com.teaml.iq.volunteer.ui.campaign

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
class CampaignPresenter<V : CampaignMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , CampaignMvpPresenter<V> {

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        mvpView.showDetailFragment()
    }
}