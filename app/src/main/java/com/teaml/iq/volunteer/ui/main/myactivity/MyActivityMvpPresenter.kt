package com.teaml.iq.volunteer.ui.main.myactivity

import com.teaml.iq.volunteer.ui.base.BaseSignInSignOutMvpPresenter
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpPresenter

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
interface MyActivityMvpPresenter<V : MyActivityMvpView> : BaseSignInSignOutMvpPresenter<V>, BaseLoadDataMvpPresenter<V> {
    fun onViewItemClick(campaignId: String, groupId: String)
}