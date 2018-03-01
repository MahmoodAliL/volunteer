package com.teaml.iq.volunteer.ui.base

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
interface BaseLoadDataWithRecyclerViewMvpView : FragmentMvpView {

    fun showRetryImg()

    fun hideRetryImg()

    fun enableLoadMore(isEnable: Boolean)

    fun setLoadingMoreDone()

    fun showProgress()

    fun hideProgress()

    fun showEmptyResult()
}