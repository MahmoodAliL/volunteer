package com.teaml.iq.volunteer.ui.main.group

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/4/2018.
 */
interface GroupMvpPresenter<V : GroupMvpView> : MvpPresenter<V> {

    fun onRetryClick()

    fun onViewPrepared()

    fun onLoadingMore()
}

