package com.teaml.iq.volunteer.ui.base

import com.teaml.iq.volunteer.data.DataManager

/**
 * Created by Mahmood Ali on 24/01/2018.
 */
class BasePresenter<V: MvpView> public constructor(dataManager: DataManager ): MvpPresenter<V> {


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
        mvpView?.openSignInActivity()
    }

    fun isViewAttached() = mvpView != null

    fun isAttached() {
        if (!isViewAttached())
            throw MvpViewNotAttachedException
    }

    object MvpViewNotAttachedException : RuntimeException ("Please call presenter.onAttach(MvpView)" +
            "before requesting data to presenter")


}