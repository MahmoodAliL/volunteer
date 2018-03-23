package com.teaml.iq.volunteer.ui.campaign.members.rate

import com.teaml.iq.volunteer.data.model.RateMembers
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpView

/**
 * Created by ali on 3/16/2018.
 */
interface RateMemberMvpView: BaseLoadDataMvpView {

    fun addMembers(members: List<RateMembers>)

    fun removeMemberView(position: Int)

    fun enableClickable(position: Int)

}