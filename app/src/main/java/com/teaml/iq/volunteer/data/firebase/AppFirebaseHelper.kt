package com.teaml.iq.volunteer.data.firebase

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_COL
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_JOINED
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_MEMBERS_LIMIT
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_QUERY_LIMIT
import com.teaml.iq.volunteer.utils.AppConstants.GROUP_COL
import com.teaml.iq.volunteer.utils.AppConstants.GROUP_QUERY_LIMIT
import com.teaml.iq.volunteer.utils.AppConstants.MEMBER_COL
import com.teaml.iq.volunteer.utils.AppConstants.USERS_COL
import java.util.*
import javax.inject.Inject


/**
 * Created by Mahmood Ali on 03/02/2018.
 */
class AppFirebaseHelper @Inject constructor() : FirebaseHelper {

    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mFirestorage = FirebaseStorage.getInstance()

    companion object {
        const val CAMPAIGN_REF_FIELD = "campaignRef"
        const val GROUP_REF_FIELD = "groupRef"
        const val JOIN_DATE_FIELD = "joinDate"
        const val USER_REF_FIELD = "userRef"
        const val IS_RATE_FIELD = "isRate"
    }

    /**
     * Firebase auth
     */

    override fun getCurrentUserEmail(): String? {
        return mFirebaseAuth.currentUser?.email
    }

    override fun getFirebaseUserAuthID(): String? = mFirebaseAuth.currentUser?.uid

    override fun signWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
            mFirebaseAuth.signInWithEmailAndPassword(email, password)

    override fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
            mFirebaseAuth.createUserWithEmailAndPassword(email, password)

    override fun sendPasswordResetEmail(email: String) = mFirebaseAuth.sendPasswordResetEmail(email)

    override fun signOut() {
        mFirebaseAuth.signOut()
    }

    /**
     * Firestore function
     */

    override fun saveProfileInfo(profileInfo: HashMap<String, Any>): Task<Void> {
        return mFirestore.collection(USERS_COL).document(mFirebaseAuth.currentUser!!.uid).set(profileInfo, SetOptions.merge())
    }

    override fun loadProfileInfo(uid: String): Task<DocumentSnapshot> {
        return mFirestore.collection(USERS_COL).document(uid).get()
    }

    override fun getUserDocRef(uid: String): DocumentReference {
        return mFirestore.collection(USERS_COL).document(uid)
    }

    override fun loadCampaignList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot> {
        val query = mFirestore.collection(CAMPAIGN_COL).limit(CAMPAIGN_QUERY_LIMIT)
                .orderBy(FbCampaign.UPLOAD_DATE, Query.Direction.DESCENDING)
        return if (lastVisibleItem != null)
            query.startAfter(lastVisibleItem).get()
        else
            query.get()
    }

    override fun loadCampaignMembers(campaignId: String, lastVisibleItem: DocumentSnapshot?): Query {
        val query = getCampaignMembersColRef(campaignId).limit(CAMPAIGN_MEMBERS_LIMIT)
                .orderBy(JOIN_DATE_FIELD)
        return if (lastVisibleItem != null)
            query.startAfter(lastVisibleItem)
        else
            query
    }

    override fun loadGroupList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot> {
        val query = mFirestore.collection(GROUP_COL).limit(GROUP_QUERY_LIMIT)
                .orderBy(FbGroup.CAMPAIGNS_NUM, Query.Direction.DESCENDING)
        return if (lastVisibleItem != null)
            query.startAfter(lastVisibleItem).get()
        else
            query.get()
    }

    override fun loadGroupCampaignList(groupId: String, lastVisibleItem: DocumentSnapshot?): Query {
        val query = mFirestore.collection(CAMPAIGN_COL)
                .whereEqualTo(GROUP_REF_FIELD, getGroupDocRef(groupId))
                .limit(CAMPAIGN_QUERY_LIMIT)
                //.orderBy(FbCampaign.UPLOAD_DATE, Query.Direction.DESCENDING)
        return if (lastVisibleItem != null)
            query.startAfter(lastVisibleItem)
        else
            query
    }

    override fun loadFirstTenGroupCampaign(groupId: String): Query {
        return mFirestore.collection(CAMPAIGN_COL)
                .whereEqualTo(GROUP_REF_FIELD, getGroupDocRef(groupId))
                /*.orderBy(FbCampaign.UPLOAD_DATE,Query.Direction.DESCENDING)*/
                .limit(AppConstants.GROUP_CAMPAIGNS_LIMIT)
    }

    override fun loadCampaignUserJoined(uid: String, lastVisibleItem: DocumentSnapshot?): Query {
        val query = mFirestore.collection(AppConstants.USERS_COL)
                .document(uid)
                .collection(AppConstants.CAMPAIGN_JOINED).limit(CAMPAIGN_QUERY_LIMIT)
        // using refraction to get the name of filed in class direct
        //.orderBy(FbCampaign.UPLOAD_DATE, Query.Direction.DESCENDING)
        return if (lastVisibleItem != null)
            query.startAfter(lastVisibleItem)
        else
            query
    }

    override fun checkUserJoinWithCampaign(campaignRef: DocumentReference): Task<QuerySnapshot> {
        return mFirestore.collection("$USERS_COL/${getFirebaseUserAuthID()}/$CAMPAIGN_JOINED")
                .whereEqualTo(CAMPAIGN_REF_FIELD, campaignRef).get()
    }

    override fun getCampaignDocRef(campaignId: String): DocumentReference {
        return mFirestore.collection(CAMPAIGN_COL).document(campaignId)
    }

    override fun getGroupDocRef(groupId: String): DocumentReference {
        return mFirestore.collection(GROUP_COL).document(groupId)
    }

    override fun getCampaignMembersColRef(campaignId: String): CollectionReference {
        return mFirestore.collection(CAMPAIGN_COL).document(campaignId).collection(MEMBER_COL)
    }


    override fun addUserToCampaign(campaignRef: DocumentReference, uid: String): Task<Long> {
        // add campaign to user

        val joinCampaignDocRef = mFirestore.document("$USERS_COL/$uid/$CAMPAIGN_JOINED/${campaignRef.id}")
        val campaignMembersDocRef = campaignRef.collection(MEMBER_COL).document(uid)
        val userDocRef = getUserDocRef(uid)

        return mFirestore.runTransaction {
            val campaignSnapshot = it.get(campaignRef)
            val userSnapshot = it.get(userDocRef)

            val maxMemberCount = campaignSnapshot.getLong(FbCampaign::maxMemberCount.name)
            val currentMemberCount = campaignSnapshot.getLong(FbCampaign::currentMemberCount.name)

            val newCurrentMemberCount = currentMemberCount + 1
            if (newCurrentMemberCount > maxMemberCount)
                throw FirebaseFirestoreException("campaign is full",
                        FirebaseFirestoreException.Code.ABORTED)
            else
                it.update(campaignRef, FbCampaign::currentMemberCount.name, newCurrentMemberCount)

            // update campaignJoinCount
            val currentCampaignJoinCount = userSnapshot.getLong(FbUserDetail::campaignJoinCount.name)
            val newCampaignJoinCount = currentCampaignJoinCount + 1
            it.update(userDocRef, FbUserDetail::campaignJoinCount.name, newCampaignJoinCount)

            // get join date of user
            val joinDate = JOIN_DATE_FIELD to FieldValue.serverTimestamp()

            // hash map to put user reference and join date in campaigns/members collection
            val memberHashMap = hashMapOf(joinDate, USER_REF_FIELD to userDocRef, IS_RATE_FIELD to DataManager.UserRate.NOT_RATED)
            it.set(campaignMembersDocRef, memberHashMap)

            // hash map to put campaign reference and join date in users/uid/joinCampaign collection
            val campaignHashMap = hashMapOf(joinDate, CAMPAIGN_REF_FIELD to campaignRef)
            it.set(joinCampaignDocRef, campaignHashMap)

            newCurrentMemberCount
        }
    }

    override fun onUserLeaveCampaign(campaignRef: DocumentReference, uid: String): Task<Long> {
        val joinCampaignDocRef = mFirestore.document("$USERS_COL/$uid/$CAMPAIGN_JOINED/${campaignRef.id}")
        val campaignMembersDocRef = campaignRef.collection(MEMBER_COL).document(uid)
        val userDocRef = getUserDocRef(uid)

        return mFirestore.runTransaction {

            val campaignSnapshot = it.get(campaignRef)
            val userSnapshot = it.get(userDocRef)

            // update currentMemberCount
            val currentMemberCount = campaignSnapshot.getLong(FbCampaign::currentMemberCount.name)
            val newCurrentMemberCount = currentMemberCount - 1
            it.update(campaignRef, FbCampaign::currentMemberCount.name, newCurrentMemberCount)

            // update campaignJoinCount
            val currentCampaignJoinCount = userSnapshot.getLong(FbUserDetail::campaignJoinCount.name)
            val newCampaignJoinCount = currentCampaignJoinCount - 1
            it.update(userDocRef, FbUserDetail::campaignJoinCount.name, newCampaignJoinCount)


            it.delete(campaignMembersDocRef)
            it.delete(joinCampaignDocRef)

            newCurrentMemberCount
        }

    }

    override fun saveCampaignInfo(campaignInfo: HashMap<String, Any>): Task<Transaction> {
        val uuid = UUID.randomUUID()
        return mFirestore.runTransaction {
            val groupDocRef = getGroupDocRef(getFirebaseUserAuthID()!!)
            val snapshot = it.get(groupDocRef)

            val newCampaignCount = snapshot.getLong(FbGroup::campaignsNum.name) + 1
            it.update(groupDocRef, FbGroup::campaignsNum.name, newCampaignCount)

            val campaignDocRef = getCampaignDocRef(uuid.toString())

            it.set(campaignDocRef, campaignInfo)
        }

    }

    // group operation

    override fun saveGroupInfo(groupInfo: HashMap<String, Any>): Task<Void> {

        val uid = getFirebaseUserAuthID()!!
        val groupDocRef = getGroupDocRef(uid)
        val userDocRef = getUserDocRef(uid)

        val userInfo = mapOf(FbUserDetail::myGroupId.name to uid)

        val batch = mFirestore.batch()
        batch.set(groupDocRef, groupInfo, SetOptions.merge())
        batch.set(userDocRef, userInfo , SetOptions.merge())
        return batch.commit()
    }


    // firebase storage

    override fun uploadProfileImg(uri: Uri): UploadTask {
        return mFirestorage.getReference(AppConstants.USER_IMG_FOLDER).child(getFirebaseUserAuthID()!!).putFile(uri)
    }

    override fun uploadGroupCoverImg(imgUri: Uri): UploadTask {
        return mFirestorage.getReference(AppConstants.GROUP_COVER_IMG_FOLDER).child(getFirebaseUserAuthID()!!).putFile(imgUri)
    }

    override fun uploadGroupLogoImg(imgUri: Uri): UploadTask {
        return mFirestorage.getReference(AppConstants.GROUP_LOGO_IMG_FOLDER).child(getFirebaseUserAuthID()!!).putFile(imgUri)
    }

    override fun uploadCampaignImg(imgUri: Uri, imgName: String): UploadTask {
        return mFirestorage.getReference(AppConstants.CAMPAIGN_IMG_FOLDER).child(imgName).putFile(imgUri)
    }
}