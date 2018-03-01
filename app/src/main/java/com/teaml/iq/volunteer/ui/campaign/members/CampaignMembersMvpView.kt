package com.teaml.iq.volunteer.ui.campaign.members

import com.teaml.iq.volunteer.data.model.CampaignMembers
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpView

/**
 * Created by Mahmood Ali on 21/02/2018.
 */
interface CampaignMembersMvpView : BaseLoadDataMvpView {

    fun addMembers(members: List<CampaignMembers>)
}