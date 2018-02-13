package com.teaml.iq.volunteer.ui.main.group

import com.teaml.iq.volunteer.data.model.GroupInfo
import com.teaml.iq.volunteer.ui.base.BaseLoadDataWithRecyclerViewMvpView

/**
 * Created by ali on 2/4/2018.
 */
interface GroupMvpView : BaseLoadDataWithRecyclerViewMvpView {

    fun updateGroups(listOfgroupInfo: MutableList<GroupInfo>)
}