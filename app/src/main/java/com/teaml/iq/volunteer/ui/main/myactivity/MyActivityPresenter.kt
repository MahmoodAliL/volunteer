package com.teaml.iq.volunteer.ui.main.myactivity

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
class MyActivityPresenter<V : MyActivityMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), MyActivityMvpPresenter<V> {

    companion object {
        val TAG: String = MyActivityPresenter::class.java.simpleName
    }

    private var lastVisibleItem: DocumentSnapshot? = null

    override fun onSignInClick() {
        mvpView?.openSignInActivity()
    }

    override fun onSignOutClick() {
        dataManager.signOut()
        dataManager.setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_OUT)
        mvpView?.openSplashActivity()
    }

    override fun decideCurrentLayout(): Int {
        return if (dataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_OUT.type)
            R.layout.myactivity_not_sign_in
        else
            R.layout.recycler_view_layout
    }


    override fun onViewPrepared() {
        if (dataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_OUT.type) {
            mvpView?.setupViewWithSignOutStatus()
        } else {
            mvpView?.setupViewWithSignInStatus()
            loadingJoinCampaignList()
        }

    }

    private fun loadingJoinCampaignList() {

        val campaignPostList = mutableListOf<CampaignPost>()
        val campaignList = mutableListOf<FbCampaign>()

        var lastVisibleItem: DocumentSnapshot? = null

        val uid = dataManager.getFirebaseUserAuthID()

        if (uid.isNullOrEmpty()) {
            mvpView?.onError(R.string.some_error)
            return
        }

        Log.e(TAG, "on loading userActivity $uid")

        dataManager.loadCampaignUserJoined(uid!!, this.lastVisibleItem).get().continueWithTask { task ->

            if (!task.isSuccessful)
                task.exception?.let { throw it }

            val result = task.result

            Log.d(TAG, "documentSize: ${result.size()}")

            if (result.isEmpty && this.lastVisibleItem == null) {
                mvpView?.showEmptyResult()
            }


            // حفظ اخر عنصر في متغر محلي لانة من المحتمل ان يحدث خطا في العمليات التالية
            if (!result.isEmpty)
                lastVisibleItem = result.documents[result.documents.size - 1]

            val tasks = mutableListOf<Task<DocumentSnapshot>>()
            result.documents.forEach {
                val campaignRef = it.getDocumentReference("campaignRef")
                tasks.add(campaignRef.get())
            }

            Tasks.whenAllSuccess<DocumentSnapshot>(tasks)
        }.continueWithTask { task ->

                    if (!task.isSuccessful)
                        task.exception?.let { throw it }

                    val tasks = mutableListOf<Task<DocumentSnapshot>>()

                    task.result.forEach {
                        val campaign = it.toObject(FbCampaign::class.java)
                        campaign.id = it.id
                        // store campaignInfo to temp list to use them when don
                        campaignList.add(campaign)
                        // store task of get groupRef to check it later
                        tasks.add(campaign.groupRef.get())
                    }

                    Tasks.whenAllSuccess<DocumentSnapshot>(tasks)
                }.addOnCompleteListener {

                    mvpView?.hideProgress()

                    if (it.isSuccessful) {

                        // exit when result is empty
                        if (it.result.isEmpty())
                            return@addOnCompleteListener

                        // only when all success load change global lastVisibleItem to local
                        // so when data is field we still load first item not last item
                        this.lastVisibleItem = lastVisibleItem

                        // using this variable to get the result from the tamp campaign info list
                        var index = 0

                        it.result.forEach {

                            val group = it.toObject(FbGroup::class.java)

                            val campaign = campaignList[index]

                            campaignPostList.add(CampaignPost(
                                    campaignId = campaign.id,
                                    title = campaign.title,
                                    coverImgName = campaign.imgName,
                                    lastModificationDate = campaign.lastModificationDate,
                                    uploadDate = campaign.uploadDate,
                                    groupId = it.id,
                                    groupName = group.name,
                                    groupLogoImg = group.logoImg
                            ))

                            index++
                        }

                        mvpView?.updateCampaign(campaignPostList)

                    } else {
                        Log.e(TAG, "${it.exception?.message}")
                        mvpView?.onError("${it.exception?.message}")
                        onError()
                    }

                }
    }

    private fun onError() {
        mvpView?.setFieldError(true)
        mvpView?.hideProgress()
        mvpView?.showProgress()
    }

    override fun onLoadingMore() {
        loadingJoinCampaignList()
    }

    override fun onRetryClick() {
        mvpView?.showProgress()
        mvpView?.hideRetryImg()

    }

}