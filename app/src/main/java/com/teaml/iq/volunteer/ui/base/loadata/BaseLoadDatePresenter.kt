package com.teaml.iq.volunteer.ui.base.loadata

import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter

/**
 * Created by Mahmood Ali on 28/02/2018.
 */
abstract class BaseLoadDatePresenter<V : BaseLoadDataMvpView>(dataManager: DataManager) : BasePresenter<V>(dataManager), BaseLoadDataMvpPresenter<V> {


    companion object {
        val TAG: String = BaseLoadDatePresenter::class.java.simpleName
    }

    protected var lastVisibleItem: DocumentSnapshot? = null

    protected var isLoadFromSwipeRefreshListener = false


    override fun onRetryClick() {
        mvpView?.enableLoadMore(true)
        mvpView?.hideRetryImg()
        mvpView?.showProgress()
    }

    abstract fun loadListData()


    override fun onViewPrepared() {
        loadListData()
    }

    override fun onLoadMore() {
        isLoadFromSwipeRefreshListener = false
        loadListData()
    }

    protected fun onLoadError() {
        mvpView?.showRetryImg()
        mvpView?.enableLoadMore(false)
    }

    protected fun showProgress() {
        if (!isLoadFromSwipeRefreshListener) {
            mvpView?.showProgress()
        }
    }

    protected fun onLoadComplete() {
        mvpView?.setLoadMoreComplete()

        if (isLoadFromSwipeRefreshListener) {
            mvpView?.hideRefreshProgress()
            Log.d(TAG, "hideRefresh")
        } else {
            mvpView?.hideProgress()
            Log.d(TAG, "hideProgress")
        }


    }

    override fun onSwipeRefresh() {

        isLoadFromSwipeRefreshListener = true
        // reset everything to zero status
        mvpView?.hideEmptyResult()
        mvpView?.hideRetryImg()
        mvpView?.clearList()
        mvpView?.enableLoadMore(true)


        // reset lastVisibleItem to load data from begging
        lastVisibleItem = null
        loadListData()
    }

}