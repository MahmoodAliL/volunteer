package com.teaml.iq.volunteer.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
class AppFirebaseHelper @Inject constructor() : FirebaseHelper {

    private val mFireBaseAuth = FirebaseAuth.getInstance()

    /**
     * Firebase auth
     */

    override fun getFirebaseUserAuthID(): String? = mFireBaseAuth.currentUser?.uid

    override fun signWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
            mFireBaseAuth.signInWithEmailAndPassword(email, password)

    override fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult> =
            mFireBaseAuth.createUserWithEmailAndPassword(email, password)

    override fun sendPasswordResetEmail(email: String) = mFireBaseAuth.sendPasswordResetEmail(email)

}