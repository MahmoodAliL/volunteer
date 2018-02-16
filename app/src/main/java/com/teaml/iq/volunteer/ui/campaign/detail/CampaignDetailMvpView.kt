package com.teaml.iq.volunteer.ui.campaign.detail

import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
interface CampaignDetailMvpView : FragmentMvpView {

    fun showRetryImg()

    fun hideRetryImg()

    fun showProgress()

    fun hideProgress()

    fun showCampaignDetail(campaign: FbCampaign, group: FbGroup)

}