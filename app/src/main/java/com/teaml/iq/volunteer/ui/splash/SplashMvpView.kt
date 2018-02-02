package com.teaml.iq.volunteer.ui.splash

import com.teaml.iq.volunteer.ui.base.MvpView

/**
 * Created by Mahmood Ali on 01/02/2018.
 */
interface SplashMvpView : MvpView {

    fun openIntroActivityForResult(requestCode: Int)

    fun openMainActivity()

    fun openBaseInfoActivity()

    fun finishActivity()


}