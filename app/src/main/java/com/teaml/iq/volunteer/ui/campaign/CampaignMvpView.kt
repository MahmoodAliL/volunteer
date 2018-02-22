package com.teaml.iq.volunteer.ui.campaign

import com.teaml.iq.volunteer.ui.base.MvpView

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
interface CampaignMvpView : MvpView {
    fun showDetailFragment()

    fun updateToolbarToDetail()

    fun updateToolbarToMember()

}