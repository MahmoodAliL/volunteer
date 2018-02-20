package com.teaml.iq.volunteer.data.firebase

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_COL
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_JOINED
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_QUERY_LIMIT
import com.teaml.iq.volunteer.utils.AppConstants.GROUP_COL
import com.teaml.iq.volunteer.utils.AppConstants.GROUP_QUERY_LIMIT
import com.teaml.iq.volunteer.utils.AppConstants.MEMBER_COL
import com.teaml.iq.volunteer.utils.AppConstants.USERS_COL
import javax.inject.Inject


/**
 * Created by Mahmood Ali on 03/02/2018.
 */
class AppFirebaseHelper @Inject constructor() : FirebaseHelper {

    private val mFirebaseAuth = FirebaseAuth.getInstance()
    private val mFirestore = FirebaseFirestore.getInstance()
    private val mFirestorage = FirebaseStorage.getInstance()

    companion object {
        const val CAMPAIGN_REF = "campaignRef"
        const val JOIN_DATE_FILED = "joinDate"
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

    // Firestore function


    override fun saveProfileInfo(profileInfo: HashMap<String, Any>): Task<Void> {
        return mFirestore.collection(USERS_COL).document(mFirebaseAuth.currentUser!!.uid).set(profileInfo, SetOptions.merge())
    }

    override fun loadProfileInfo(uid: String): Task<DocumentSnapshot> {
        return mFirestore.collection(USERS_COL).document(uid).get()
    }

    override fun getUserReference(uid: String): DocumentReference {
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

    override fun loadGroupList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot> {
        val query = mFirestore.collection(GROUP_COL).limit(GROUP_QUERY_LIMIT)
                .orderBy(FbGroup.CAMPAIGNS_NUM, Query.Direction.DESCENDING)
        return if (lastVisibleItem != null)
            query.startAfter(lastVisibleItem).get()
        else
            query.get()
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
                .whereEqualTo(CAMPAIGN_REF, campaignRef).get()
    }

    override fun getCampaignReference(campaignId: String): DocumentReference {
        return mFirestore.collection(CAMPAIGN_COL).document(campaignId)
    }

    override fun getGroupReference(groupId: String): DocumentReference {
        return mFirestore.collection(GROUP_COL).document(groupId)
    }

    override fun addUserToCampaign(campaignRef: DocumentReference, uid: String): Task<DocumentSnapshot> {
        // add campaign to user

        val joinCampaignDocRef = mFirestore.document("$USERS_COL/$uid/$CAMPAIGN_JOINED/${campaignRef.id}")
        val campaignMembersDocRef = campaignRef.collection(MEMBER_COL).document(uid)

        return mFirestore.runTransaction {
            val campaignSnapshot = it.get(campaignRef)
            val maxMemberCount = campaignSnapshot.getLong(FbCampaign::maxMemberCount.name)
            val currentMemberCount = campaignSnapshot.getLong(FbCampaign::currentMemberCount.name)

            val newCurrentMemberCount = currentMemberCount + 1
            if (newCurrentMemberCount > maxMemberCount)
                throw FirebaseFirestoreException("campaign is full",
                        FirebaseFirestoreException.Code.ABORTED)
            else
                it.update(campaignRef, FbCampaign::currentMemberCount.name, newCurrentMemberCount)

            val hashMap = hashMapOf<String, Any>("joinDate" to FieldValue.serverTimestamp())
            it.set(campaignMembersDocRef, hashMap)
            // add campaign reference
            hashMap[CAMPAIGN_REF] = campaignRef
            it.set(joinCampaignDocRef, hashMap)

            campaignSnapshot
        }
    }

    override fun onUserLeaveCampaign(campaignRef: DocumentReference, uid: String): Task<DocumentSnapshot> {
        val joinCampaignDocRef = mFirestore.document("$USERS_COL/$uid/$CAMPAIGN_JOINED/${campaignRef.id}")
        val campaignMembersDocRef = campaignRef.collection(MEMBER_COL).document(uid)

        return mFirestore.runTransaction {

            val campaignSnapshot = it.get(campaignRef)

            val currentMemberCount = campaignSnapshot.getLong(FbCampaign::currentMemberCount.name)

            val newCurrentMemberCount = currentMemberCount - 1
            it.update(campaignRef, FbCampaign::currentMemberCount.name, newCurrentMemberCount)

            it.delete(campaignMembersDocRef)
            it.delete(joinCampaignDocRef)

            campaignSnapshot
        }

    }
    // firebase storage

    override fun uploadProfileImg(uri: Uri): UploadTask {
        return mFirestorage.getReference(AppConstants.USER_IMG_FOLDER).child(getFirebaseUserAuthID()!!).putFile(uri)
    }

}