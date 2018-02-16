package com.teaml.iq.volunteer.ui.campaign.detail

import android.util.Log
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
class CampaignDetailPresenter<V : CampaignDetailMvpView> @Inject constructor(dataManager: DataManager) : BasePresenter<V>(dataManager), CampaignDetailMvpPresenter<V> {

    companion object {
        val TAG: String = CampaignDetailPresenter::class.java.simpleName
    }

    var campaign: FbCampaign? = null

    override fun loadCampaignDetail(campaignId: String, groupId: String) {

        mvpView?.getBaseActivity()?.let { baseActivity ->

            mvpView?.showProgress()
            dataManager.getCampaignReference(campaignId).get()
                    .continueWithTask {
                        //save campaign detail to using them later
                        campaign = it.result.toObject(FbCampaign::class.java)
                        campaign?.groupRef?.get()

                    }.addOnCompleteListener (baseActivity) {

                        mvpView?.hideProgress()

                        if (it.isSuccessful) {
                            val group = it.result.toObject(FbGroup::class.java)
                            campaign?.let { it
                                mvpView?.showCampaignDetail(it, group)
                            }
                        } else {
                            Log.e(TAG, "on loading campaign detail error", it.exception)
                            mvpView?.showRetryImg()
                        }
                    }

        }

    }
}