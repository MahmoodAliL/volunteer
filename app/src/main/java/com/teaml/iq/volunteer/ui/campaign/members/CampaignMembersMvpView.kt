package com.teaml.iq.volunteer.ui.campaign.members

import com.teaml.iq.volunteer.data.model.CampaignMembers
import com.teaml.iq.volunteer.ui.base.BaseLoadDataWithRecyclerViewMvpView

/**
 * Created by Mahmood Ali on 21/02/2018.
 */
interface CampaignMembersMvpView : BaseLoadDataWithRecyclerViewMvpView {

    fun hideEmptyResult()

    fun addMembers(members: List<CampaignMembers>)
}