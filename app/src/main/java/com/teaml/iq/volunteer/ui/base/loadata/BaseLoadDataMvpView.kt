package com.teaml.iq.volunteer.ui.base.loadata

import com.teaml.iq.volunteer.ui.base.FragmentMvpView

/**
 * Created by Mahmood Ali on 27/02/2018.
 */
interface BaseLoadDataMvpView : FragmentMvpView {

    fun showRetryImg()

    fun hideRetryImg()

    fun hideRefreshProgress()

    fun enableSwipeRefreshLayout(isEnable: Boolean)

    fun enableLoadMore(isEnable: Boolean)

    fun setLoadMoreComplete()

    fun showProgress()

    fun hideProgress()

    fun isProgressShow(): Boolean

    fun showEmptyResult()

    fun hideEmptyResult()

    fun clearList()
}