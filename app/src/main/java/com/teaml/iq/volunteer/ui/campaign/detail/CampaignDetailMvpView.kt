package com.teaml.iq.volunteer.ui.campaign.detail

import android.support.annotation.StringRes
import com.google.firebase.firestore.GeoPoint
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
interface CampaignDetailMvpView : FragmentMvpView {

    fun showRetryImg()

    fun hideRetryImg()

    fun showProgress()

    fun hideProgress()

    fun updateJoinBtnToJoin()

    fun updateJoinBtnToLeave()

    fun updateJoinBtnToRateMember()

    fun openSignInActivity()

    fun showOnLeaveCampaignDialog()

    fun showCampaignMembersFragment(campaignId: String)

    fun showRateMembersFragment(campaignId: String)

    fun onUserOwnerCampaign()

    fun openGoogleMap(geoPoint: GeoPoint)

    fun updateCampaignDetail(campaign: FbCampaign)

    fun updateCurrentMembers(currentMembers: Long)

    fun disableJoinBtn(@StringRes btnText: Int = R.string.join , @StringRes note: Int)

    fun showCampaignDetail(campaign: FbCampaign, group: FbGroup)

    fun openGroupActivity(groupId: String)

}