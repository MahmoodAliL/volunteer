package com.teaml.iq.volunteer.data

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
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

    /**
     * Firebase Auth
     */

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

    /**
     * Firestore
     */

    override fun saveBasicUserInfo(basicUserInfo: HashMap<String, Any>): Task<Void> =
            firebaseHelper.saveBasicUserInfo(basicUserInfo)

    override fun loadProfileInfo(uid: String): Task<DocumentSnapshot> = firebaseHelper.loadProfileInfo(uid)

    override fun loadCampaignList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot> =
            firebaseHelper.loadCampaignList(lastVisibleItem)

    override fun loadGroupList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot> =
            firebaseHelper.loadGroupList(lastVisibleItem)

    override fun getUserReference(uid: String): DocumentReference =
            firebaseHelper.getUserReference(uid)
}