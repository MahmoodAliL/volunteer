package com.teaml.iq.volunteer.ui.campaign.members.rate

import com.google.android.gms.tasks.Tasks
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.data.model.RateMembers
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDatePresenter
import com.teaml.iq.volunteer.utils.AppConstants
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by ali on 3/16/2018.
 */
class RateMemberPresenter<V : RateMemberMvpView> @Inject constructor(dataManager: DataManager)
    : BaseLoadDatePresenter<V>(dataManager), RateMemberMvpPresenter<V> {

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
            } catch (e: Exception) {
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

    override fun onHelpfulClick(campaignId: String, userId: String, position: Int) {
        dataManager.onHelpfulRate(campaignId, userId)
                .addOnSuccessListener {
                    onUserRateSuccess(position)
                }
                .addOnFailureListener {
                    onUserRateError(position)
                }
    }

    override fun onUnhelpfulClick(campaignId: String, userId: String, position: Int) {
        dataManager.onUnhelpfulRate(campaignId, userId)
                .addOnSuccessListener {
                    onUserRateSuccess(position)
                }
                .addOnFailureListener {
                    onUserRateError(position)
                }
    }

    override fun onNotAttendClick(campaignId: String, userId: String, position: Int) {

        dataManager.onNotAttendRate(campaignId, userId)
                .addOnSuccessListener {
                    onUserRateSuccess(position)
                }
                .addOnFailureListener {
                    onUserRateError(position)
                }

    }

    private fun onUserRateSuccess(position: Int) {
        mvpView?.removeMemberView(position)
        mvpView?.showMessage(R.string.user_rated_done)
    }

    private fun onUserRateError(position: Int) {
        mvpView?.enableClickable(position)
        mvpView?.showMessage(R.string.some_error)
    }


}