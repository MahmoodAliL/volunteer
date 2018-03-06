package com.teaml.iq.volunteer.ui.group.edit

import com.teaml.iq.volunteer.ui.group.create.CreateGroupMvpPresenter

/**
 * Created by Mahmood Ali on 05/03/2018.
 */
interface EditGroupMvpPresenter<V : EditGroupMvpView>: CreateGroupMvpPresenter<V> {
    fun loadGroupDetail()
}