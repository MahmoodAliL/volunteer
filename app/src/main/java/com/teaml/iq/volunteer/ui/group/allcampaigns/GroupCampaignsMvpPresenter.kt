package com.teaml.iq.volunteer.ui.group.view_all_campaign

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/21/2018.
 */

interface GroupCampaignsMvpPresenter<V: GroupCampaignsMvpView> : MvpPresenter<V> {

    fun onLoadingMore()

    fun onRetryClick()

    fun onViewPrepared(groupId: String)

}