package com.teaml.iq.volunteer.ui.group.view_all_campaign

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.AppConstants
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ali on 2/21/2018.
 */
class GroupCampaignsPresenter<V : GroupCampaignsMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), GroupCampaignsMvpPresenter<V> {

    private var lastVisibleItem: DocumentSnapshot? = null

    private var groupId: String? = null

    companion object {
        val TAG: String = GroupCampaignsFragment::class.java.simpleName
    }

    override fun onViewPrepared(groupId: String) {
        this.groupId = groupId
        loadCampaignList()
    }

    override fun onRetryClick() {
        mvpView?.setFieldError(false)
        mvpView?.hideRetryImg()
        mvpView?.showProgress()
        loadCampaignList()
    }

    private fun loadCampaignList() {

        mvpView?.showProgress()

        doAsync {

            try {

                val loadGroupCampaignsTask = dataManager.loadGroupCampaignList(groupId!!, lastVisibleItem).get()
                Tasks.await(loadGroupCampaignsTask, AppConstants.REQUEST_LONG_TIME_OUT, TimeUnit.SECONDS)

                val documentSnapshot = loadGroupCampaignsTask.result
                // check if data is empty to prevent index out or rang exception
                // when preform
                if (!documentSnapshot.isEmpty)
                    lastVisibleItem = documentSnapshot.documents[documentSnapshot.size() - 1]

                val campaignList = mutableListOf<GroupCampaigns>()
                documentSnapshot.forEach {
                    val campaign = it.toObject(FbCampaign::class.java)
                    campaign.id = it.id
                    // store campaignInfo to temp list to use them when don
                    campaignList.add(GroupCampaigns(
                            campaignId = campaign.id,
                            title = campaign.title,
                            imgName = campaign.imgName,
                            uploadDate = campaign.uploadDate
                    ))
                }

                uiThread { mvpView?.updateCampaign(campaignList) }

            } catch (e: Exception) {
                Log.e(TAG, "on loading group campaigns -> ", e)
                uiThread { onError() }
            } finally {
                uiThread { mvpView?.hideProgress() }
            }
        }

    }

    private fun onError() {
        mvpView?.setFieldError(true)
        mvpView?.showRetryImg()
    }

    override fun onLoadingMore() {
        loadCampaignList()
    }


}