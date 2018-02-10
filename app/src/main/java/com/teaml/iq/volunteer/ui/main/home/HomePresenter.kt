package com.teaml.iq.volunteer.ui.main.home

import android.util.Log
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.Campaign
import com.teaml.iq.volunteer.ui.base.BasePresenter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import javax.inject.Inject

/**
 * Created by ali on 2/4/2018.
 */
class HomePresenter<V : HomeMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), HomeMvpPresenter<V> {

    companion object {
        val TAG: String = HomePresenter::class.java.simpleName
    }

    override fun onViewPrepared() {
        Log.d(TAG, "onViewPrepared")
        loadingDataFromServer()
    }


    override fun onRetryClick() {
        mvpView?.setFieldError(false)
        mvpView?.hideRetryImg()
        loadingDataFromServer()
    }

    fun loadingDataFromServer() {

        mvpView?.showProgress()

        doAsync {
            Thread.sleep(1000)

            uiThread {
                mvpView?.setLoadingMoreDone()
                mvpView?.hideProgress()
            }

            if (Random().nextBoolean()) {
                val campaigns = mutableListOf<Campaign>()

                for (i in 1..5) {
                    campaigns.add(Campaign(
                            orgImg = "logo1.jpg",
                            title = "The title of campaign will be here",
                            orgName = "Team-L",
                            uploadDate = 0L,
                            coverImg = "campaign.jpg"
                    ))
                }
                onComplete(campaigns)
            } else {
                onError()
            }
        }

    }

    fun onComplete(data: MutableList<Campaign>) {
        mvpView?.updateCampaign(data)
    }

    fun onError() {
        mvpView?.setFieldError(true)
        mvpView?.showRetryImg()
    }

    override fun onLoadingMore() {
        loadingDataFromServer()
    }


}