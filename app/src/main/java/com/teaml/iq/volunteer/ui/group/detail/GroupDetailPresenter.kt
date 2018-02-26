package com.teaml.iq.volunteer.ui.group.detail

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.AppConstants
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ali on 2/18/2018.
 */
class GroupDetailPresenter<V : GroupDetailMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), GroupDetailMvpPresenter<V> {


    companion object {
        val TAG: String = GroupDetailPresenter::class.java.simpleName
    }

    override fun loadGroupDetail(groupId: String) {

        mvpView?.showProgress()

        doAsync {
            val campaigns = mutableListOf<GroupCampaigns>()
            try {
                // wait group info to get result
                val groupInfoTask = dataManager.getGroupDocRef(groupId).get()
                Tasks.await(groupInfoTask, AppConstants.REQUEST_LONG_TIME_OUT, TimeUnit.SECONDS)
                // get result of group info
                val group: FbGroup = groupInfoTask.result.toObject(FbGroup::class.java)

                // wait group campaigns task
                val groupCampaignsTasks = dataManager.loadFirstTenGroupCampaign(groupId).get()
                Tasks.await(groupCampaignsTasks, AppConstants.REQUEST_LONG_TIME_OUT, TimeUnit.SECONDS)
                // get result
                val result = groupCampaignsTasks.result
                Log.e(TAG, result.size().toString())
                val fbCampaign = result.toObjects(FbCampaign::class.java)

                val listOfCampaignInfo = fbCampaign.mapIndexedTo(campaigns) { index, value ->
                    GroupCampaigns(
                            campaignId = result.documents[index].id,
                            title = value.title,
                            imgName = value.imgName,
                            uploadDate = value.uploadDate
                    )
                }

                uiThread {
                    if (listOfCampaignInfo.size >= 6)
                        mvpView?.showViewAll()

                    mvpView?.showGroupDetail(group)
                    mvpView?.updateCampaign(listOfCampaignInfo)
                }

            } catch (e: Exception) {
                Log.e(TAG, "on loading detail error", e)
                uiThread { mvpView?.showRetryImg() }
            } finally {
                uiThread { mvpView?.hideProgress() }
            }
        }

    }


    override fun onViewAllClick() {
        mvpView?.showGroupCampaignsFragment()
    }

}