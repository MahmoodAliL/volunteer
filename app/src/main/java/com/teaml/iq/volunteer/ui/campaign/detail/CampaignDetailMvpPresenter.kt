package com.teaml.iq.volunteer.ui.campaign.detail

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
interface CampaignDetailMvpPresenter<V : CampaignDetailMvpView> : MvpPresenter<V> {


    fun prepareLoadCampaign(campaignId: String, groupId: String)

    fun onRetryImgClick()

    fun onJoinClick()

    fun onOpenMapClick()
}