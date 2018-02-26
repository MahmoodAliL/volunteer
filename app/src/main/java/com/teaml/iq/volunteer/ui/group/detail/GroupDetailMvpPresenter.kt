package com.teaml.iq.volunteer.ui.group.detail

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/18/2018.
 */
interface GroupDetailMvpPresenter<V: GroupDetailMvpView>: MvpPresenter<V> {

    fun loadGroupDetail(groupId: String)

    fun onViewAllClick()

}