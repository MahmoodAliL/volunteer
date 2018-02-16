package com.teaml.iq.volunteer.ui.profile.info

import android.view.Menu
import android.view.MenuInflater
import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
interface ProfileInfoMvpPresenter<V : ProfileInfoMvpView> : MvpPresenter<V> {

    fun onRetryImgClick()

    fun fetchProfileInfo(uid: String)

    fun onActionEditClick()

    fun onCreateOptionMenu(menu: Menu?, inflater: MenuInflater?)
}