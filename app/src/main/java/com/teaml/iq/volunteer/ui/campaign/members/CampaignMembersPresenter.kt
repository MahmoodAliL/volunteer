package com.teaml.iq.volunteer.ui.campaign.members

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.CampaignMembers
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.toDateString
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 21/02/2018.
 */
class CampaignMembersPresenter<V : CampaignMembersMvpView> @Inject constructor(dataManager: DataManager) : BasePresenter<V>(dataManager), CampaignMembersMvpPresenter<V> {

    companion object {
        val TAG: String = CampaignMembersPresenter::class.java.simpleName
    }

    private var lastVisibleItem: DocumentSnapshot? = null
    private var campaignId = ""

    override fun onViewPrepared(campaignId: String) {
        this.campaignId = campaignId
        loadMembersList()
    }

    private fun loadMembersList() {

        doAsync {

            Log.d(TAG, "on loading ")

            try {

                val membersCampaignTask = dataManager.loadCampaignMembers(campaignId, lastVisibleItem).get()
                Tasks.await(membersCampaignTask, AppConstants.REQUEST_LONG_TIME_OUT, TimeUnit.SECONDS)

                val members = mutableListOf<CampaignMembers>()
                // if result is empty
                if (!membersCampaignTask.result.isEmpty) {
                    lastVisibleItem = membersCampaignTask.result.documents[membersCampaignTask.result.size() - 1]
                } else if (lastVisibleItem == null) {
                    uiThread { mvpView?.showEmptyResult() }
                }

                membersCampaignTask.result.forEach {
                    // get user reference
                    val userRef = it.getDocumentReference("userRef")
                    // get join date
                    val joinDate = it.getDate("joinDate")
                    // save user id to use it later
                    val uid = userRef.id
                    // wait user info task to get result
                    val userInfoTask = userRef.get()
                    Tasks.await(userInfoTask, AppConstants.REQUEST_SHORT_TIME_OUT, TimeUnit.SECONDS)
                    // get info after result is complete
                    val userInfo = userInfoTask.result.toObject(FbUserDetail::class.java)

                    members.add(CampaignMembers(
                            uid = uid,
                            imgName = userInfo.img,
                            userName = userInfo.name,
                            joinDate = joinDate.toDateString()
                    ))
                }

                Log.d(TAG, "onAddMembers")
                uiThread { mvpView?.addMembers(members) }


            } catch (e: Exception) {
                uiThread {
                    mvpView?.setFieldError(true)
                    mvpView?.showRetryImg()
                }

                Log.e(TAG, "on error", e)
            } finally {
                uiThread {
                    mvpView?.setLoadingMoreDone()
                    mvpView?.hideProgress()
                }

            }
        }
    }

    override fun onLoadingMore() {
        loadMembersList()
    }


    override fun onRetryClick() {
        mvpView?.showProgress()
        mvpView?.hideRetryImg()

        loadMembersList()
    }

}