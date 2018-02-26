package com.teaml.iq.volunteer.ui.profile.edit

import android.net.Uri
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by Mahmood Ali on 14/02/2018.
 */
interface EditProfileMvpView : FragmentMvpView {

    fun showProfileInfo(profileInfo: FbUserDetail)

    fun showEmail(email: String)

    fun onFetchProfileInfoError(msg: String)

    fun updateProfileImg(uri: Uri)

    fun updateProfileImg(imgName: String)

    fun openCropImage()

    fun showReadExternalStorageRationale()

    fun showProfileInfoFragment()
}