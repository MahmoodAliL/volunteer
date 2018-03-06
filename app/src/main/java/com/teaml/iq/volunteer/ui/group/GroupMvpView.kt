package com.teaml.iq.volunteer.ui.group

import com.teaml.iq.volunteer.ui.base.MvpView

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
interface GroupMvpView : MvpView {

    fun showGroupDetailFragment()

    fun showCreateGroupFragment()

    fun updateToolbarToGroupDetail()

    fun updateToolbarToGroupCampaigns()
}