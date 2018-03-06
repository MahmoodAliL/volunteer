package com.teaml.iq.volunteer.ui.main.myaccount

import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.ui.base.BaseSignInSignOutMvpView

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
interface MyAccountMvpView : BaseSignInSignOutMvpView {

    fun showProfileInfo(profileInfo: FbUserDetail)

    fun openProfileActivity(uid: String)

    fun openGroupActivityWithCreateGroup()

    fun openGroupActivityWithGroupDetail(uid: String)
}