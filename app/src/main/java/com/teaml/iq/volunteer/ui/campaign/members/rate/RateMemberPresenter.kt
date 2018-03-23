package com.teaml.iq.volunteer.ui.campaign.members.rate

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentReference
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.data.model.RateMembers
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDatePresenter
import com.teaml.iq.volunteer.ui.campaign.members.CampaignMembersPresenter
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.CommonUtils
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ali on 3/16/2018.
 */
class RateMemberPresenter<V:RateMemberMvpView> @Inject constructor(dataManager: DataManager)
    :BaseLoadDatePresenter<V>(dataManager), RateMemberMvpPresenter<V> {

    var campaignId = ""


    override fun onViewPrepared(campaignId: String) {
        this.campaignId = campaignId
        loadListData()
    }
    override fun loadListData() {
        showProgress()
        doAsync {
            try {

                val memberTask = dataManager.loadRateMembers(campaignId, lastVisibleItem).get()
                Tasks.await(memberTask, AppConstants.REQUEST_LONG_TIME_OUT, TimeUnit.SECONDS)

                val memberList = mutableListOf<RateMembers>()

                if (!memberTask.result.isEmpty) {
                    lastVisibleItem = memberTask.result.documents[memberTask.result.documents.size - 1]
                } else if (lastVisibleItem == null) {
                    uiThread { mvpView?.showEmptyResult() }
                } else {
                    mvpView?.enableLoadMore(false)
                }

                memberTask.result.forEach {
                    val userRef = it.getDocumentReference("userRef")
                    val joinDate = it.getDate("joinDate")
                    val userId = userRef.id

                    val userInfoTask = userRef.get()
                    Tasks.await(userInfoTask, AppConstants.REQUEST_LONG_TIME_OUT, TimeUnit.SECONDS)

                    val userInfo = userInfoTask.result.toObject(FbUserDetail::class.java)

                    memberList.add(RateMembers(
                            uid = userId,
                            userName = userInfo.name,
                            imgName = userInfo.img,
                            joinDate = joinDate,
                            lastModificationDate = userInfo.lastModificationDate
                            ))


                }
                uiThread { mvpView?.addMembers(memberList) }
            } catch (e:Exception){
                uiThread {
                    mvpView?.enableLoadMore(true)
                    mvpView?.showRetryImg()
                }
            } finally {
                uiThread {
                    onLoadComplete()
                    mvpView?.hideProgress()
                }
            }
        }
    }
    override fun onHelpfulClick(campaignId: String, userId:String, position: Int) {
        dataManager.onHelpfulRate(campaignId,userId)
                .addOnSuccessListener {
                    mvpView?.removeMemberView(position)
                    mvpView?.showMessage("rating done")
                }
                .addOnFailureListener {
                    mvpView?.enableClickable(position)
                    mvpView?.showMessage("error")
                }
    }

    override fun onUnhelpfulClick(campaignId: String, userId: String, position: Int) {
        dataManager.onUnhelpfulRate(campaignId,userId)
                .addOnSuccessListener {
                    mvpView?.removeMemberView(position)
                    mvpView?.showMessage("rating done")
                }
                .addOnFailureListener {
                    mvpView?.enableClickable(position)
                    mvpView?.showMessage("error")
                }
    }

    override fun onNotAttendClick(campaignId: String, userId: String, position: Int) {
        mvpView?.let { view ->
            dataManager.onNotAttendRate(campaignId, userId)
                    .addOnSuccessListener {
                        view.removeMemberView(position)
                        view.showMessage("rating done")
                    }
                    .addOnFailureListener {
                        view.enableClickable(position)
                        view.showMessage("error")
                    }
        }
    }

}