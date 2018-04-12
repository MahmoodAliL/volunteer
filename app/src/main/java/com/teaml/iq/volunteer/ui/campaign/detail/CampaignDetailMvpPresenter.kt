package com.teaml.iq.volunteer.ui.campaign.detail

import android.view.Menu
import android.view.MenuInflater
import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
interface CampaignDetailMvpPresenter<V : CampaignDetailMvpView> : MvpPresenter<V> {


    fun prepareLoadCampaign(campaignId: String, groupId: String)

    fun onRetryImgClick()

    fun onJoinClick()

    fun onOpenMapClick()

    fun onMembersClick()

    fun onGroupImgClick()

    fun onUserLeaveCampaign()

    fun onCreateOptionMenu(menu: Menu?, inflater: MenuInflater?)

    fun onActionEditClick()

    fun onBackStackChanged(backStackEntryCount: Int?)
}