package com.teaml.iq.volunteer.ui.main.home

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.DocumentSnapshot
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.toDateString
import javax.inject.Inject

/**
 * Created by ali on 2/4/2018.
 */
class HomePresenter<V : HomeMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), HomeMvpPresenter<V> {

    private var lastVisibleItem: DocumentSnapshot? = null


    companion object {
        val TAG: String = HomePresenter::class.java.simpleName
    }

    override fun onViewPrepared() {
        loadCampaignList()
    }

    override fun onRetryClick() {
        mvpView?.setFieldError(false)
        mvpView?.hideRetryImg()
        mvpView?.showProgress()
        loadCampaignList()
    }

    private fun loadCampaignList() {

        val campaignPostList = mutableListOf<CampaignPost>()
        val campaignList = mutableListOf<FbCampaign>()

        var lastVisibleItem: DocumentSnapshot? = null

        dataManager.loadCampaignList(this.lastVisibleItem)
                .continueWithTask { task ->

                    if (!task.isSuccessful)
                        task.exception?.let { throw it }

                    val documentSnapshot = task.result
                    // check if data is empty to prevent index out or rang exception
                    // when preform
                    if (!task.result.isEmpty)
                        lastVisibleItem = documentSnapshot.documents[documentSnapshot.size() - 1]

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
                                    uploadDate = campaign.uploadDate.toDateString(),
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
        mvpView?.showRetryImg()
    }

    override fun onLoadingMore() {
       loadCampaignList()
    }


}