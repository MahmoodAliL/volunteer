package com.teaml.iq.volunteer.ui.profile.info

import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
interface ProfileInfoMvpView : FragmentMvpView {
    fun showRetryImg()

    fun hideRetryImg()

    fun showProgress()

    fun hideProgress()

    fun showProfileInfo(profileInfo: FbUserDetail)

}