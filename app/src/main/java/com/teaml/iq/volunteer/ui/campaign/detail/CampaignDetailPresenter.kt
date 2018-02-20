package com.teaml.iq.volunteer.ui.campaign.detail

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.GeoPoint
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.ui.base.BasePresenter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
class CampaignDetailPresenter<V : CampaignDetailMvpView> @Inject constructor(dataManager: DataManager) : BasePresenter<V>(dataManager), CampaignDetailMvpPresenter<V> {


    companion object {
        val TAG: String = CampaignDetailPresenter::class.java.simpleName
    }

    // flags
    private var isSignIn = false
    private var isJoin = false

    // store detail
    private var profileInfo: FbUserDetail? = null
    private var geoPoint: GeoPoint? = null

    private var campaignId = ""
    private var groupId = ""


    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        isSignIn = dataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_WITH_EMAIL.type

    }


    override fun prepareLoadCampaign(campaignId: String, groupId: String) {
        this.campaignId = campaignId
        this.groupId = groupId


        loadCampaign()
    }


    override fun onRetryImgClick() {
        mvpView?.hideRetryImg()
        mvpView?.showProgress()
        loadCampaign()
    }

    override fun onOpenMapClick() {
        geoPoint?.let {
            mvpView?.openGoogleMap(it)
        }
    }

    private fun loadCampaign() {

        val uid = dataManager.getFirebaseUserAuthID()

        mvpView?.showProgress()
        Log.d(TAG, "start loading")
        // doAsync in anko using when want to run in background
        doAsync {

            try {

                Log.d(TAG, "loading campaign detail")
                // loading campaign detail
                val loadCampaignDetail = dataManager.getCampaignReference(campaignId).get()
                Tasks.await(loadCampaignDetail, 20, TimeUnit.SECONDS)
                // save campaign result
                val campaign = loadCampaignDetail.result.toObject(FbCampaign::class.java)
                // store location of campaign
                geoPoint = campaign.location

                // loading group detail
                Log.d(TAG, "loading group detail ")
                val loadGroupDetail = dataManager.getGroupReference(groupId).get()
                Tasks.await(loadGroupDetail, 20, TimeUnit.SECONDS)
                // save group result
                val group = loadGroupDetail.result.toObject(FbGroup::class.java)


                // check if user sign in or not to determine task need
                if (isSignIn && uid != null) {
                    Log.d(TAG, "on sign in ")
                    val campaignRef = dataManager.getCampaignReference(campaignId)
                    val checkUserJoinWithCampaign = dataManager.checkUserJoinWithCampaign(campaignRef)
                    // wait task until complete
                    Tasks.await(checkUserJoinWithCampaign, 20, TimeUnit.SECONDS)
                    // is  user join
                    if (checkUserJoinWithCampaign.result.isEmpty) {
                        // load user data to using them to check user
                        val loadProfileInfo = dataManager.loadProfileInfo(uid)
                        Tasks.await(loadProfileInfo, 20, TimeUnit.SECONDS)

                        profileInfo = loadProfileInfo.result.toObject(FbUserDetail::class.java)
                        Log.d(TAG, "not join loading profile info")
                        // to check if user can join to campaign if not why can't join
                        Log.d(TAG, "start check if user joint to campaign")
                        uiThread {
                            canJoinToCampaign(profileInfo!!, campaign)
                        }

                        isJoin = false

                    } else {
                        isJoin = true

                        uiThread { mvpView?.updateJoinBtnToLeave() }
                    }

                }


                // نقوم باستدعى الدالة في uiThread لاننا نتعامل مع الوجهة المستخدم هذا مختصر بعض الشي
                uiThread {
                    mvpView?.showCampaignDetail(campaign, group)
                }

            } catch (e: Exception) {
                Log.e(TAG, "exception in try", e)
                uiThread {
                    mvpView?.showRetryImg()
                }
            } finally {
                Log.d(TAG, "finally")
                uiThread {
                    mvpView?.hideProgress()
                }
            }

        }

    }


    private fun canJoinToCampaign(profileInfo: FbUserDetail, campaign: FbCampaign): Boolean {

        if (campaign.currentMemberCount >= campaign.maxMemberCount) {
            mvpView?.disableJoinBtn(R.string.full_campaign)
            return false
        }

        if (profileInfo.gender != campaign.gender && campaign.gender != DataManager.UserGender.ANY.type) {

            val stringRed = if (campaign.gender == DataManager.UserGender.MALE.type)
                R.string.this_campaign_for_male
            else
                R.string.this_campaign_for_female

            mvpView?.disableJoinBtn(stringRed)

            return false
        }

        // check age user if accepted or not
        // current date
        val today = Calendar.getInstance()

        // birth of date
        val birthOfDate = Calendar.getInstance()
        birthOfDate.time = profileInfo.birthOfDay

        // different between current age and this year
        var age = today.get(Calendar.YEAR) - birthOfDate.get(Calendar.YEAR)

        // let see day of year is = 100 and old
        if (today.get(Calendar.DAY_OF_YEAR) < birthOfDate.get(Calendar.DAY_OF_YEAR))
            age--

        if (age < campaign.age) {
            mvpView?.disableJoinBtn(R.string.your_age_is_less_then_required)
            return false
        }

        return true
    }


    override fun onJoinClick() {

        if (isSignIn) {

            if (isJoin)
                onUserLeaveCampaign()
            else
                onUserJoinToCampaign()

        } else {
            mvpView?.openSignInActivity()
        }
    }

    private fun onUserLeaveCampaign() {
        mvpView?.getBaseActivity()?.let { baseActivity ->
            dataManager.getFirebaseUserAuthID()?.let { uid ->
                val campaignRef = dataManager.getCampaignReference(campaignId)
                mvpView?.showLoading()
                dataManager.onUserLeaveCampaign(campaignRef, uid).addOnCompleteListener(baseActivity) {

                    mvpView?.hideLoading()

                    if (it.isSuccessful) {
                        val campaign = it.result.toObject(FbCampaign::class.java)
                        geoPoint = campaign.location
                        mvpView?.updateCampaignDetail(campaign)
                        mvpView?.updateJoinBtnToJoin()
                        isJoin = true

                    } else {
                        Log.e(TAG, it.exception?.message)
                        mvpView?.onError(R.string.some_error)
                    }
                }
            }
        }
    }

    private fun onUserJoinToCampaign() {
        mvpView?.getBaseActivity()?.let { baseActivity ->
            dataManager.getFirebaseUserAuthID()?.let { uid ->
                val campaignRef = dataManager.getCampaignReference(campaignId)
                mvpView?.showLoading()
                dataManager.addUserToCampaign(campaignRef, uid).addOnCompleteListener(baseActivity) {

                    mvpView?.hideLoading()

                    if (it.isSuccessful) {
                        val campaign = it.result.toObject(FbCampaign::class.java)
                        // get last update of location and campaign in general
                        geoPoint = campaign.location
                        mvpView?.updateCampaignDetail(campaign)
                        mvpView?.updateJoinBtnToLeave()
                        isJoin = true

                    } else {
                        Log.e(TAG, it.exception?.message)
                        mvpView?.onError(R.string.some_error)
                    }
                }
            }
        }

    }


}