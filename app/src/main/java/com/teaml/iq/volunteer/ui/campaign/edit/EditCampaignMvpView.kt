package com.teaml.iq.volunteer.ui.campaign.edit

import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.ui.campaign.add.AddCampaignMvpView

/**
 * Created by Mahmood Ali on 14/03/2018.
 */
interface EditCampaignMvpView : AddCampaignMvpView {

    fun showCampaignDetail(campaignInfo: FbCampaign)

    fun onLoadCampaignError()
}