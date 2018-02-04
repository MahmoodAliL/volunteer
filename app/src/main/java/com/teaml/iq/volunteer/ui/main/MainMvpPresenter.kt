package com.teaml.iq.volunteer.ui.main

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/3/2018.
 */
interface MainMvpPresenter<V : MainMvpView> : MvpPresenter<V> {

    fun onNavigationItemSelected(itemId: Int)

}