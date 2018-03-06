package com.teaml.iq.volunteer.ui.group

import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
interface GroupMvpPresenter<V : GroupMvpView> : MvpPresenter<V> {
    fun onBackStackChangedListener(backStackEntryCount: Int)

    fun decideCurrentFragment(fragmentType: Int)
}