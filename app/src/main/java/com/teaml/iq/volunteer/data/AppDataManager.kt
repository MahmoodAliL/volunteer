package com.teaml.iq.volunteer.data

import android.content.Context
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.firestore.DocumentReference
import com.teaml.iq.volunteer.data.firebase.FirebaseHelper
import com.teaml.iq.volunteer.data.pref.PreferenceHelper
import com.teaml.iq.volunteer.di.annotation.ApplicationContext
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 24/01/2018.
 */
class AppDataManager @Inject constructor(
        val  preferenceHelper: PreferenceHelper ,
        val firebaseHelper: FirebaseHelper
) : DataManager {

    /**
     * SharedPreferences
     */

    override fun setFirstStart(value: Boolean) = preferenceHelper.setFirstStart(value)

    override fun isFirstStart(): Boolean = preferenceHelper.isFirstStart()

    override fun hasBaseProfileInfo(): Boolean {
        return false
    }

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

    /**
     * Firestore
     */

    override fun saveBasicUserInfo(basicUserInfo: HashMap<String, Any>): Task<Void> =
            firebaseHelper.saveBasicUserInfo(basicUserInfo)
}