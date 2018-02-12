package com.teaml.iq.volunteer.ui.main.group

import com.teaml.iq.volunteer.data.model.GroupPost
import com.teaml.iq.volunteer.ui.base.BaseMainMvpView

/**
 * Created by ali on 2/4/2018.
 */
interface GroupMvpView : BaseMainMvpView {

    fun updateGroups(groupPosts: MutableList<GroupPost>)
}