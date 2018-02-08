package com.teaml.iq.volunteer.ui.splash

import android.app.Activity.RESULT_OK
import android.content.Intent
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

        when {

            dataManager.isFirstStart() -> mvpView?.openIntroActivityForResult(INTRO_ACTIVITY_RC)

            dataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_WITH_EMAIL.type
                    && !dataManager.hasBasicProfileInfo() -> mvpView?.openBaseInfoActivity()

            else -> mvpView?.openMainActivity()
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == INTRO_ACTIVITY_RC ) {

            if (resultCode == RESULT_OK) {
                dataManager.setFirstStart(false)
            } else {
                //User cancelled the intro so we'll finish this activity too.
                dataManager.setFirstStart(true)
                mvpView?.finishActivity()
            }
        }
    }
}