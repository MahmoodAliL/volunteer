package com.teaml.iq.volunteer.ui.group.detail

import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by ali on 2/18/2018.
 */
interface GroupDetailMvpView : FragmentMvpView {

    fun showRetryImg()

    fun hideRetryImg()

    fun showProgress()

    fun hideProgress()

    fun showFabAddGroup()

    fun onMyGroupShow()

    fun showEmptyResult()

    fun showViewAll()

    fun showGroupDetail(fbGroup: FbGroup)

    fun showGroupCampaignsFragment()

    fun updateCampaign(list: MutableList<GroupCampaigns>)



}