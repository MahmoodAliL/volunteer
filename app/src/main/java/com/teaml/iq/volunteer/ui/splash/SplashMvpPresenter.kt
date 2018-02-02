package com.teaml.iq.volunteer.ui.splash

import android.content.Intent
import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by Mahmood Ali on 01/02/2018.
 */
interface SplashMvpPresenter<V : SplashMvpView> : MvpPresenter<V>{
    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}