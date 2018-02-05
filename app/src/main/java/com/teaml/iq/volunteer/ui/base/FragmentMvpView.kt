package com.teaml.iq.volunteer.ui.base

import android.app.Activity

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
interface FragmentMvpView : MvpView {

    fun getBaseActivity(): Activity?
}