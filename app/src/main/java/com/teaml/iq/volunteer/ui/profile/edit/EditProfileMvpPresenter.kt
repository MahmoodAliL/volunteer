package com.teaml.iq.volunteer.ui.profile.edit

import android.content.Intent
import com.teaml.iq.volunteer.ui.base.MvpPresenter
import java.util.*

/**
 * Created by Mahmood Ali on 14/02/2018.
 */
interface EditProfileMvpPresenter<V : EditProfileMvpView> : MvpPresenter<V> {

    fun fetchProfileInfo()

    fun onActionDoneClick(name: String, bio: String, phoneNumber: String, birthOfDay: Date)

    fun onProfileImgClick()

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)

    fun onRequestReadExternalStoragePermissionAfterRationale()

}