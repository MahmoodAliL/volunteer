package com.teaml.iq.volunteer.data

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.*
import com.google.firebase.storage.UploadTask
import com.teaml.iq.volunteer.data.firebase.FirebaseHelper
import com.teaml.iq.volunteer.data.pref.PreferenceHelper
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 24/01/2018.
 */
class AppDataManager @Inject constructor(
        val preferenceHelper: PreferenceHelper,
        val firebaseHelper: FirebaseHelper) : DataManager {


    /**
     * SharedPreferences
     */

    override fun setFirstStart(value: Boolean) = preferenceHelper.setFirstStart(value)

    override fun isFirstStart(): Boolean = preferenceHelper.isFirstStart()

    override fun setHasBasicProfileInfo(value: Boolean) {
        preferenceHelper.setHasBasicProfileInfo(value)
    }

    override fun hasBasicProfileInfo(): Boolean = preferenceHelper.hasBasicProfileInfo()

    override fun setCurrentUserLoggedInMode(mode: DataManager.LoggedInMode) {
        preferenceHelper.setCurrentUserLoggedInMode(mode)
    }

    override fun getCurrentUserLoggedInMode(): Int = preferenceHelper.getCurrentUserLoggedInMode()

    override fun getCurrentUserEmail(): String? = firebaseHelper.getCurrentUserEmail()

    override fun hasGroup(): Boolean = preferenceHelper.hasGroup()

    override fun setHasGroup(hasGroup: Boolean) {
        preferenceHelper.setHasGroup(hasGroup)
    }

    // firebase auth
    override fun getFirebaseUserAuthID(): String? = firebaseHelper.getFirebaseUserAuthID()

    override fun signWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
            firebaseHelper.signWithEmailAndPassword(email, password)

    override fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
            firebaseHelper.createUserWithEmailAndPassword(email, password)

    override fun sendPasswordResetEmail(email: String): Task<Void> =
            firebaseHelper.sendPasswordResetEmail(email)

    override fun signOut() {
        firebaseHelper.signOut()
    }

    override fun checkOldPassword(oldPassword: String): Task<Void> =
            firebaseHelper.checkOldPassword(oldPassword)

    override fun onUpdatePassword(newPassword: String): Task<Void> =
            firebaseHelper.onUpdatePassword(newPassword)

    /**
     * Firestore
     */

    override fun getUserDocRef(uid: String): DocumentReference =
            firebaseHelper.getUserDocRef(uid)

    override fun getCampaignDocRef(campaignId: String): DocumentReference =
            firebaseHelper.getCampaignDocRef(campaignId)

    override fun getGroupDocRef(groupId: String): DocumentReference =
            firebaseHelper.getGroupDocRef(groupId)

    override fun saveProfileInfo(profileInfo: HashMap<String, Any>): Task<Void> =
            firebaseHelper.saveProfileInfo(profileInfo)

    override fun loadCampaignMembers(campaignId: String, lastVisibleItem: DocumentSnapshot?): Query =
            firebaseHelper.loadCampaignMembers(campaignId, lastVisibleItem)

    override fun loadRateMembers(campaignId: String, lastVisibleItem: DocumentSnapshot?): Query =
        firebaseHelper.loadRateMembers(campaignId,lastVisibleItem)

    override fun loadProfileInfo(uid: String): Task<DocumentSnapshot> = firebaseHelper.loadProfileInfo(uid)

    override fun loadCampaignList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot> =
            firebaseHelper.loadCampaignList(lastVisibleItem)

    override fun loadGroupList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot> =
            firebaseHelper.loadGroupList(lastVisibleItem)

    override fun loadFirstTenGroupCampaign(groupId: String): Query =
            firebaseHelper.loadFirstTenGroupCampaign(groupId)

    override fun loadGroupCampaignList(groupId: String, lastVisibleItem: DocumentSnapshot?): Query =
            firebaseHelper.loadGroupCampaignList(groupId, lastVisibleItem)

    override fun loadCampaignUserJoined(uid: String, lastVisibleItem: DocumentSnapshot?): Query =
            firebaseHelper.loadCampaignUserJoined(uid, lastVisibleItem)

    override fun getCampaignMembersColRef(campaignId: String): CollectionReference =
            firebaseHelper.getCampaignMembersColRef(campaignId)

    override fun incrementCampaignView(campaignId: String): Task<Long> {
        return firebaseHelper.incrementCampaignView(campaignId)
    }

    // rate fun
    override fun onHelpfulRate(campaignId: String, userId: String): Task<Long> =
         firebaseHelper.onHelpfulRate(campaignId,userId)

    override fun onUnhelpfulRate(campaignId: String, userId: String): Task<Long> =
        firebaseHelper.onUnhelpfulRate(campaignId,userId)

    override fun onNotAttendRate(campaignId: String, userId: String): Task<Long> =
        firebaseHelper.onNotAttendRate(campaignId,userId)


    // campaign detail
    override fun checkUserJoinWithCampaign(campaignRef: DocumentReference): Task<QuerySnapshot> =
            firebaseHelper.checkUserJoinWithCampaign(campaignRef)

    override fun addUserToCampaign(campaignRef: DocumentReference, uid: String): Task<Long> =
            firebaseHelper.addUserToCampaign(campaignRef, uid)

    override fun onUserLeaveCampaign(campaignRef: DocumentReference, uid: String): Task<Long> =
            firebaseHelper.onUserLeaveCampaign(campaignRef, uid)

    // group operation

    override fun saveGroupInfo(groupInfo: HashMap<String, Any>): Task<Void> =
            firebaseHelper.saveGroupInfo(groupInfo)

    override fun saveCampaignInfo(campaignInfo: HashMap<String, Any>): Task<Transaction> =
            firebaseHelper.saveCampaignInfo(campaignInfo)

    // leaderboard

    override fun loadTopUsers(lastVisibleItem: DocumentSnapshot?): Query =
            firebaseHelper.loadTopUsers(lastVisibleItem)

    /**
     * firebase storage
     */

    override fun uploadProfileImg(uri: Uri): UploadTask = firebaseHelper.uploadProfileImg(uri)

    override fun uploadGroupCoverImg(imgUri: Uri): UploadTask {
        return firebaseHelper.uploadGroupCoverImg(imgUri)
    }

    override fun uploadGroupLogoImg(imgUri: Uri): UploadTask {
        return firebaseHelper.uploadGroupLogoImg(imgUri)
    }

    override fun uploadCampaignImg(imgUri: Uri, imgName: String): UploadTask {
        return firebaseHelper.uploadCampaignImg(imgUri, imgName)
    }
}