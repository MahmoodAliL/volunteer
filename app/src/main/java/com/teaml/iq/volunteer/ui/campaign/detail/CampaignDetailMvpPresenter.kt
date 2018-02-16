package com.teaml.iq.volunteer.ui.campaign.detail

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
interface CampaignDetailMvpPresenter<V : CampaignDetailMvpView> : MvpPresenter<V> {

    fun loadCampaignDetail(campaignId: String, groupId: String)
}