package com.teaml.iq.volunteer.ui.main.leaderborad

import com.teaml.iq.volunteer.data.model.TopUser
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpView

/**
 * Created by Mahmood Ali on 29/03/2018.
 */
interface LeaderboardMvpView : BaseLoadDataMvpView {
    fun addNewItems(listOfTopUser: List<TopUser>)

    fun setUserId(uid: String)

    fun openProfileActivity(uid: String)
}