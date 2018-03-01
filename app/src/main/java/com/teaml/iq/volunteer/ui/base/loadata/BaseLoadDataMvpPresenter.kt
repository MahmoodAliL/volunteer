package com.teaml.iq.volunteer.ui.base.loadata

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 28/02/2018.
 */
interface BaseLoadDataMvpPresenter<V : BaseLoadDataMvpView> : MvpPresenter<V> {

    fun onRetryClick()

    fun onViewPrepared()

    fun onLoadMore()

    fun onSwipeRefresh()
}