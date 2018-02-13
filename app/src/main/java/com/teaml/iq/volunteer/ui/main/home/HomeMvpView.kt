package com.teaml.iq.volunteer.ui.main.home

import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.ui.base.BaseLoadDataWithRecyclerViewMvpView

/**
 * Created by ali on 2/4/2018.
 */
interface HomeMvpView : BaseLoadDataWithRecyclerViewMvpView {

    fun updateCampaign(campaignPosts: MutableList<CampaignPost>)

}