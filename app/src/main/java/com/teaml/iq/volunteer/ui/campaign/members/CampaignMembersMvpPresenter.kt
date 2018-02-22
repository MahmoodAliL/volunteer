package com.teaml.iq.volunteer.ui.campaign.members

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 21/02/2018.
 */
interface CampaignMembersMvpPresenter<V : CampaignMembersMvpView> : MvpPresenter<V> {

    fun onViewPrepared(campaignId: String)

    fun onLoadingMore()

    fun onRetryClick()

}