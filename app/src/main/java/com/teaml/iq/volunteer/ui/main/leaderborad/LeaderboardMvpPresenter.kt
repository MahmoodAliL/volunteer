package com.teaml.iq.volunteer.ui.main.leaderborad

import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpPresenter

/**
 * Created by Mahmood Ali on 29/03/2018.
 */
interface LeaderboardMvpPresenter<V : LeaderboardMvpView> : BaseLoadDataMvpPresenter<V> {

    fun onViewItemClick(uid: String)
}