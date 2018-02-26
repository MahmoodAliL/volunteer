package com.teaml.iq.volunteer.ui.group.view_all_campaign

import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.BaseLoadDataWithRecyclerViewMvpView

/**
 * Created by ali on 2/21/2018.
 */
interface GroupCampaignsMvpView : BaseLoadDataWithRecyclerViewMvpView {

    fun updateCampaign(campaigns: MutableList<GroupCampaigns>)

}