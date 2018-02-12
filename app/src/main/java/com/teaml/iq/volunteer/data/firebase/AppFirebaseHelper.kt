package com.teaml.iq.volunteer.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_COL
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_QUERY_LIMIT
import com.teaml.iq.volunteer.utils.AppConstants.USERS_COL
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
class AppFirebaseHelper @Inject constructor() : FirebaseHelper {

    private val mFireBaseAuth = FirebaseAuth.getInstance()
    private val mFirestore = FirebaseFirestore.getInstance()

    /**
     * Firebase auth
     */

    override fun getFirebaseUserAuthID(): String? = mFireBaseAuth.currentUser?.uid

    override fun signWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
            mFireBaseAuth.signInWithEmailAndPassword(email, password)

    override fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
            mFireBaseAuth.createUserWithEmailAndPassword(email, password)

    override fun sendPasswordResetEmail(email: String) = mFireBaseAuth.sendPasswordResetEmail(email)


    // Firestore function

    override fun saveBasicUserInfo(basicUserInfo: HashMap<String, Any>): Task<Void> {
        return mFirestore.collection(USERS_COL).document(mFireBaseAuth.currentUser!!.uid).set(basicUserInfo)
    }

    override fun loadProfileInfo(): Task<DocumentSnapshot> {
        return mFirestore.collection(USERS_COL).document(mFireBaseAuth.currentUser?.uid ?: "").get()
    }

    override fun loadCampaignList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot> {
        val query =  mFirestore.collection(CAMPAIGN_COL).limit(CAMPAIGN_QUERY_LIMIT)
                .orderBy(FbCampaign.UPLOAD_DATE, Query.Direction.DESCENDING)
        return if (lastVisibleItem != null)
            query.startAfter(lastVisibleItem).get()
        else
           query.get()
    }
}