package com.teaml.iq.volunteer.ui.group.create

import android.net.Uri
import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by ali on 2/23/2018.
 */
interface CreateGroupMvpView : FragmentMvpView {

    fun openCropCoverImg()

    fun openCropLogoImg()

    fun updateCoverImg(uri: Uri)

    fun updateLogoImg(uri: Uri)

    fun showGroupDetailFragment(groupId: String)

    fun showReadExternalStorageRationale()
}