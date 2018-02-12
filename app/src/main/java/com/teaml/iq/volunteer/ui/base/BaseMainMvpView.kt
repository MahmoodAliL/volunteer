package com.teaml.iq.volunteer.ui.base

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
interface BaseMainMvpView : MvpView {

    fun showRetryImg()

    fun hideRetryImg()

    fun setFieldError(value: Boolean)

    fun setLoadingMoreDone()

    fun showProgress()

    fun hideProgress()

    fun showEmptyResult()
}