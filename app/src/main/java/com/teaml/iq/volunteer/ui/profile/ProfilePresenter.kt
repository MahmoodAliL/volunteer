package com.teaml.iq.volunteer.ui.profile

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
class ProfilePresenter<V : ProfileMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), ProfileMvpPresenter<V> {

    private var uid: String? = null

    override fun loadUserInfo(uid: String?) {
        this.uid = uid
        uid?.let {
            mvpView?.showProfileInfoFragment(it)
        }
    }


    override fun onBackStackChangedListener(backStackEntryCount: Int) {
        if (backStackEntryCount == 0 ) {
            mvpView?.updateToolbarToProfileInfo()
        } else {
            mvpView?.updateToolbarToEditProfile()
        }
    }

}