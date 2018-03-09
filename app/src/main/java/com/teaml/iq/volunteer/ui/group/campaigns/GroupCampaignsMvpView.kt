package com.teaml.iq.volunteer.ui.group.view_all_campaign

import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpView

/**
 * Created by ali on 2/21/2018.
 */
interface GroupCampaignsMvpView : BaseLoadDataMvpView {

    fun updateCampaign(campaigns: List<GroupCampaigns>)

}