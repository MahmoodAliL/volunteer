package com.teaml.iq.volunteer.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.teaml.iq.volunteer.data.DataManager
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
class AppFirebaseHelper @Inject constructor() : FirebaseHelper {

    companion object {
        const val USERS_COL = "users"
    }
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

    override fun saveBasicUserInfo(basicUserInfo: HashMap<String, Any>): Task<Void> {
        return mFirestore.collection(USERS_COL).document(mFireBaseAuth.currentUser!!.uid).set(basicUserInfo)
    }


}