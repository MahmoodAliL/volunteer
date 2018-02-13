package com.teaml.iq.volunteer.ui.base

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
interface BaseSignInSignOutMvpPresenter<V : MvpView> : MvpPresenter<V> {

    fun onSignInClick()

    fun onSignOutClick()

    fun decideCurrentLayout(): Int

    fun onViewPrepared()
}