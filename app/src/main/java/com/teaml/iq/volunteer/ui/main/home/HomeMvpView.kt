package com.teaml.iq.volunteer.ui.main.home

import com.teaml.iq.volunteer.data.model.Campaign
import com.teaml.iq.volunteer.ui.base.MvpView

/**
 * Created by ali on 2/4/2018.
 */
interface HomeMvpView : MvpView {

    fun showRetryImg()

    fun hideRetryImg()

    fun setFieldError(value: Boolean)

    fun updateCampaign(campaigns: MutableList<Campaign>)

    fun setLoadingMoreDone()

    fun showProgress()

    fun hideProgress()



}