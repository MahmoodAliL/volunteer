package com.teaml.iq.volunteer.ui.account

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
interface AccountMvpPresenter<V : AccountMvpView> : MvpPresenter<V> {

    fun decideCurrentFragment(currentFragmentId: Int)
}