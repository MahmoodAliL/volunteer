package com.teaml.iq.volunteer.ui.group.detail

import android.view.Menu
import android.view.MenuInflater
import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/18/2018.
 */
interface GroupDetailMvpPresenter<V: GroupDetailMvpView>: MvpPresenter<V> {


    fun onViewAllClick()

    fun onFabClick()

    fun onViewPrepared(groupId: String)

    fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?)

    fun onRetryImgClick()

}