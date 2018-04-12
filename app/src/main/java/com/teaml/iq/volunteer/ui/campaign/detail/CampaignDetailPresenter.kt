package com.teaml.iq.volunteer.ui.campaign.detail

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
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

    private var isOwnerCampaign = false



    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        isSignIn = dataManager.getCurrentUserLoggedInMode() == DataManager.LoggedInMode.LOGGED_IN_WITH_EMAIL.type

    }


    override fun prepareLoadCampaign(campaignId: String, groupId: String) {
        this.campaignId = campaignId
        this.groupId = groupId

        val uid = dataManager.getFirebaseUserAuthID()

        if (uid != null)
            isOwnerCampaign =  uid == groupId

        loadCampaign()
        updateCampaignDetail()

    }

    private fun updateCampaignDetail() {
        mvpView?.getBaseActivity()?.let {

            dataManager.getCampaignDocRef(campaignId).addSnapshotListener(it) { documentSnapshot, firebaseFirestoreException ->

                if (firebaseFirestoreException != null) {
                    Log.e(TAG, "on snapshot document", firebaseFirestoreException)
                    return@addSnapshotListener
                }

                val campaign = documentSnapshot.toObject(FbCampaign::class.java)
                mvpView?.updateCampaignDetail(campaign)
            }
        }
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

    override fun onMembersClick() {
        mvpView?.showCampaignMembersFragment(campaignId)
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
                val loadCampaignDetail = dataManager.getCampaignDocRef(campaignId).get()
                Tasks.await(loadCampaignDetail, 20, TimeUnit.SECONDS)
                // save campaign result
                val campaign = loadCampaignDetail.result.toObject(FbCampaign::class.java)
                // store location of campaign
                geoPoint = campaign.location

                // loading group detail
                Log.d(TAG, "loading group detail ")
                val loadGroupDetail = dataManager.getGroupDocRef(groupId).get()
                Tasks.await(loadGroupDetail, 20, TimeUnit.SECONDS)
                // save group result
                val group = loadGroupDetail.result.toObject(FbGroup::class.java)

                // check if user sign in or not to determine task need
                if (isSignIn && uid != null) {
                    Log.d(TAG, "on sign in ")

                    // if user is owner this campaign then  user can rate member only if start date
                    if (isOwnerCampaign) {
                        Log.d(TAG, "user is owner of this campaign")
                        uiThread {
                            canRateMemberNow(campaign)
                            mvpView?.onUserOwnerCampaign()
                        }

                    }
                    // check is user is join or not
                    else {
                        val campaignRef = dataManager.getCampaignDocRef(campaignId)
                        val checkUserJoinWithCampaignTask = dataManager.checkUserJoinWithCampaign(campaignRef)
                        // wait task until complete
                        Tasks.await(checkUserJoinWithCampaignTask, 20, TimeUnit.SECONDS)
                        // is  user join
                        if (checkUserJoinWithCampaignTask.result.isEmpty) {
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
                            uiThread {
                                mvpView?.updateJoinBtnToLeave()
                                checkCanLeaveCampaign(campaign)
                            }
                        }
                    }

                }

                // when campaign loaded success increment viewsCounts
                dataManager.incrementCampaignView(campaignId)
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


    override fun onBackStackChanged(backStackEntryCount: Int?) {
        if (backStackEntryCount != null)
            mvpView?.setEditMenuItemVisible(backStackEntryCount <= 0)
    }

    private fun checkCanLeaveCampaign(campaign: FbCampaign) {
        val now = Calendar.getInstance()
        val startDate = Calendar.getInstance()
        startDate.time = campaign.startDate

        val currentDayOfYear = now[Calendar.DAY_OF_YEAR]
        now[Calendar.DAY_OF_YEAR] = currentDayOfYear + 1

        if (startDate.timeInMillis < now.timeInMillis) {
            mvpView?.disableJoinBtn(R.string.leave, R.string.not_allow_leave_now)
        }
    }

    private fun canRateMemberNow(campaign: FbCampaign) {

        val startDate = Calendar.getInstance()
        startDate.time = campaign.startDate

        val nowDate = Calendar.getInstance()
        val currentDayOfYear = nowDate.get(Calendar.DAY_OF_YEAR)
        nowDate.set(Calendar.DAY_OF_YEAR, currentDayOfYear - 1)

        if (startDate.timeInMillis <= nowDate.timeInMillis) {
            mvpView?.updateJoinBtnToRateMember()
        } else {
            mvpView?.disableJoinBtn(R.string.rate_member, R.string.not_allow_rate_member_now)
        }
    }

    private fun canJoinToCampaign(profileInfo: FbUserDetail, campaign: FbCampaign): Boolean {
        val nowDate = Calendar.getInstance()
        val currentDayOfYear = nowDate[Calendar.DAY_OF_YEAR]
        nowDate[Calendar.DAY_OF_YEAR] = currentDayOfYear + 1

        val startDate = Calendar.getInstance()
        startDate.time = campaign.startDate

        if (startDate.timeInMillis <= nowDate.timeInMillis) {
            mvpView?.disableJoinBtn(note = R.string.not_allow_join_now)
            return false
        }

        if (campaign.currentMemberCount >= campaign.maxMemberCount) {
            mvpView?.disableJoinBtn(note = R.string.full_campaign)
            return false
        }

        if (profileInfo.gender != campaign.gender && campaign.gender != DataManager.UserGender.ANY.type) {

            val stringRed = if (campaign.gender == DataManager.UserGender.MALE.type)
                R.string.this_campaign_for_male
            else
                R.string.this_campaign_for_female

            mvpView?.disableJoinBtn(note = stringRed)

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

        // let see day of year is = 100 and day of year of birth day is 101 so it need one day to
        // to complete has day old
        if (today.get(Calendar.DAY_OF_YEAR) < birthOfDate.get(Calendar.DAY_OF_YEAR))
            age--

        if (age < campaign.age) {
            mvpView?.disableJoinBtn(note = R.string.your_age_is_less_then_required)
            return false
        }

        return true
    }

    override fun onGroupImgClick() {
        mvpView?.openGroupActivity(groupId)
    }


    override fun onJoinClick() {

        if (mvpView?.isNetworkConnection() == false) {
            mvpView?.onError(R.string.connection_error)
            return
        }

        if (isSignIn) {

            when {
                isOwnerCampaign -> mvpView?.showRateMembersFragment(campaignId)
                isJoin -> mvpView?.showOnLeaveCampaignDialog()
                else -> onUserJoinToCampaign()
            }

        } else {
            mvpView?.openSignInActivity()
        }
    }

    override fun onUserLeaveCampaign() {
        mvpView?.getBaseActivity()?.let { baseActivity ->
            dataManager.getFirebaseUserAuthID()?.let { uid ->
                val campaignRef = dataManager.getCampaignDocRef(campaignId)
                mvpView?.showLoading()
                dataManager.onUserLeaveCampaign(campaignRef, uid).addOnCompleteListener(baseActivity) {

                    mvpView?.hideLoading()

                    if (it.isSuccessful) {

                        mvpView?.updateCurrentMembers(it.result)
                        mvpView?.updateJoinBtnToJoin()
                        isJoin = false

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
                val campaignRef = dataManager.getCampaignDocRef(campaignId)
                mvpView?.showLoading()
                dataManager.addUserToCampaign(campaignRef, uid).addOnCompleteListener(baseActivity) {

                    mvpView?.hideLoading()

                    if (it.isSuccessful) {

                        mvpView?.updateCurrentMembers(it.result)
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

    override fun onCreateOptionMenu(menu: Menu?, inflater: MenuInflater?) {
        if (isOwnerCampaign)
            inflater?.inflate(R.menu.campaign_owner_menu, menu)
    }


    override fun onActionEditClick() {
        mvpView?.showEditCampaignFragment(campaignId)
    }


}