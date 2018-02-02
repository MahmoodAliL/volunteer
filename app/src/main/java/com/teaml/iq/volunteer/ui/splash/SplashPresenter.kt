package com.teaml.iq.volunteer.ui.splash

import android.content.Intent
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 01/02/2018.
 */
class SplashPresenter<V : SplashMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , SplashMvpPresenter<V> {

    companion object {

        private val TAG: String = SplashPresenter::class.java.simpleName
        private const val INTRO_ACTIVITY_RC = 1
    }


    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        decideNextActivity()

    }

    private fun decideNextActivity() {

        if (dataManager.isFirstStart()) {
            mvpView?.openIntroActivityForResult(INTRO_ACTIVITY_RC)
            mvpView?.showMessage("true")
        } else {
            mvpView?.showMessage("something")
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == INTRO_ACTIVITY_RC ) {
            if (resultCode == IntroActivity.RESULT_OK) {
                dataManager.setFirstStart(false)
            } else {
                // user cancel this activity
                dataManager.setFirstStart(true)
                mvpView?.finishActivity()
            }
        }
    }
}