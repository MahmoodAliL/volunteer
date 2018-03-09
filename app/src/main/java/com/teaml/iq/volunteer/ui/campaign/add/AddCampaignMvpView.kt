package com.teaml.iq.volunteer.ui.campaign.add

import android.net.Uri
import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by ali on 2/25/2018.
 */
interface AddCampaignMvpView : FragmentMvpView {

    fun openCropImg()

    fun updateImg(uri: Uri)

    fun showReadExternalStorageRationale()

    fun showMyGroupFragment(groupId: String)
}