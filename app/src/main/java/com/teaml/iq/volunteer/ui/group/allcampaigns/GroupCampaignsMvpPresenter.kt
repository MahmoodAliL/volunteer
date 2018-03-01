package com.teaml.iq.volunteer.ui.group.view_all_campaign

import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataMvpPresenter

/**
 * Created by ali on 2/21/2018.
 */

interface GroupCampaignsMvpPresenter<V: GroupCampaignsMvpView> : BaseLoadDataMvpPresenter<V> {

    fun onViewPrepared(groupId: String)

}