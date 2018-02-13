package com.teaml.iq.volunteer.ui.base

import com.teaml.iq.volunteer.data.DataManager

/**
 * Created by Mahmood Ali on 24/01/2018.
 */
open class BasePresenter<V : MvpView> constructor(dataManager: DataManager) : MvpPresenter<V> {


    var dataManager: DataManager
        private set

    init {
        this.dataManager = dataManager
    }

    var mvpView: V? = null
        private set

    override fun onAttach(mvpView: V) {
        this.mvpView = mvpView
    }

    override fun onDetach() {
        mvpView = null

    }

    override fun setUserAsLoggedOut() {
        dataManager.signOut()
        dataManager.setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_OUT)
        mvpView?.openSplashActivity()
    }

    fun isViewAttached() = mvpView != null

    fun isAttached() {
        if (!isViewAttached())
            throw MvpViewNotAttachedException()
    }

    // make it static class
    companion object {
        class MvpViewNotAttachedException : RuntimeException("Please call mPresenter.onAttach(MvpView)" +
                "before requesting data to mPresenter")
    }


}