package com.teaml.iq.volunteer.ui.profile.info

import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
interface ProfileInfoMvpView : FragmentMvpView {
    fun showRetryImg()

    fun hideRetryImg()

    fun showProgress()

    fun hideProgress()

    fun showEditProfileInfo(uid: String)

    fun updateProfileImg(currentProfileImg: String)

    fun updateUserName(currentUserName: String)

    fun updateUserBio(currentUserBio: String)

    fun updateGender(gender: String)

    fun updateBirthOfDay(currentBirthOfDay: String)

    fun updatePhoneNumber(currentPhoneNumber: String)

    fun showAndUpdateEmail(email: String)

}