package com.teaml.iq.volunteer.ui.main.myactivity

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
class MyActivityPresenter<V : MyActivityMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V> (dataManager) , MyActivityMvpPresenter<V>{



    override fun onSignInClick() {
        mvpView?.openSignInActivity()
    }

    override fun onSignOutClick() {
        dataManager.signOut()
        dataManager.setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_OUT)
        mvpView?.openSplashActivity()
    }

    override fun decideCurrentLayout(): Int {
        return if (dataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_OUT.type)
            R.layout.myactivity_not_sign_in
        else
            R.layout.recycler_view_layout
    }


    override fun onViewPrepared() {
        if (dataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_OUT.type)
            mvpView?.setupViewWithSignOutStatus()
        else
            mvpView?.setupViewWithSignInStatus()
    }

}