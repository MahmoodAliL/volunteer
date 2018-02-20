package com.teaml.iq.volunteer.ui.main.myactivity

import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.ui.base.BaseLoadDataWithRecyclerViewMvpView
import com.teaml.iq.volunteer.ui.base.BaseSignInSignOutMvpView

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
interface MyActivityMvpView : BaseSignInSignOutMvpView, BaseLoadDataWithRecyclerViewMvpView {
    fun updateCampaign(campaignPosts: MutableList<CampaignPost>)

}
