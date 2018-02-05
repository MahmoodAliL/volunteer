package com.teaml.iq.volunteer.ui.account

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
class AccountPresenter<V : AccountMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , AccountMvpPresenter<V> {


    override fun decideCurrentFragment(currentFragmentId: Int) {
        when (currentFragmentId) {
            AccountActivity.CurrentFragment.BASE_INFO_FRAGMENT.type -> mvpView?.showBaseProfileInfoFragment()

            AccountActivity.CurrentFragment.SING_IN_FRAGMENT.type -> mvpView?.showSignInFragment()
        }
    }

}