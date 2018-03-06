package com.teaml.iq.volunteer.ui.group.create

import android.content.Intent
import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/23/2018.
 */
interface CreateGroupMvpPresenter<V : CreateGroupMvpView> : MvpPresenter<V> {

    fun onActionDoneClick(name: String, bio: String)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)

    fun onRequestReadExternalStoragePermission()

    fun onCoverImgClick()

    fun onLogoImgClick()


}