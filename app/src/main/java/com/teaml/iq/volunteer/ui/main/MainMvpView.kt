package com.teaml.iq.volunteer.ui.main

import android.content.Context
import com.teaml.iq.volunteer.ui.base.MvpView

/**
 * Created by ali on 2/3/2018.
 */
interface MainMvpView : MvpView {

    fun viewHomeFragment()
    fun viewGroupFragment()
    fun viewProfileFragment()

}