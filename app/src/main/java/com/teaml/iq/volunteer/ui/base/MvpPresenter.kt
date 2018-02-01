package com.teaml.iq.volunteer.ui.base

/**
 * Created by Mahmood Ali on 20/01/2018.
 *
 * Every presenter in the app must either implement this interface or extend BasePresenter
 * indicating the MvpView type that wants to be attached with.
 */
interface MvpPresenter<V: MvpView> {

    fun onAttach(mvpView: V)

    fun onDetach()

    fun setUserAsLoggedOut()

}