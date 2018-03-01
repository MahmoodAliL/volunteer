package com.teaml.iq.volunteer.ui.campaign.members

import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpPresenter

/**
 * Created by Mahmood Ali on 21/02/2018.
 */
interface CampaignMembersMvpPresenter<V : CampaignMembersMvpView> : BaseLoadDataMvpPresenter<V> {

    fun onViewPrepared(campaignId: String)

}