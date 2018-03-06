package com.teaml.iq.volunteer.ui.group.edit

import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.ui.group.create.CreateGroupMvpView

/**
 * Created by Mahmood Ali on 05/03/2018.
 */
interface EditGroupMvpView : CreateGroupMvpView {
    fun showGroupDetail(groupInfo: FbGroup)

    fun onLoadGroupInfoError()


}