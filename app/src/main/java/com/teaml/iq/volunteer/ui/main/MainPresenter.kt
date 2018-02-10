package com.teaml.iq.volunteer.ui.main

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by ali on 2/3/2018.
 */
class MainPresenter<V : MainMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , MainMvpPresenter<V> {

    override fun onNavigationItemSelected(itemId: Int) {
        when(itemId){
            R.id.action_home -> {
                mvpView?.showHomeFragment()
            }

            R.id.action_group -> {
                mvpView?.showGroupFragment()
            }


        }
    }
}