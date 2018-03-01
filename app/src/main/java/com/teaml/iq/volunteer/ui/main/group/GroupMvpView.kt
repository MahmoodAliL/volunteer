package com.teaml.iq.volunteer.ui.main.group

import com.teaml.iq.volunteer.data.model.GroupInfo
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpView

/**
 * Created by ali on 2/4/2018.
 */
interface GroupMvpView : BaseLoadDataMvpView {

    fun updateGroups(listOfGroupInfo: MutableList<GroupInfo>)
}