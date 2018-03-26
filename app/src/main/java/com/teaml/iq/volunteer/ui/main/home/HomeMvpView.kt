package com.teaml.iq.volunteer.ui.main.home

import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpView

/**
 * Created by ali on 2/4/2018.
 */
interface HomeMvpView : BaseLoadDataMvpView {

    fun addNewItems(newItems: MutableList<CampaignPost>)

    fun openCampaignActivityWithDetailFragment(campaignId: String, groupId: String)
}