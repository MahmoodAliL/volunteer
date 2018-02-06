package com.teaml.iq.volunteer.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
interface FbAuth {

    fun getFirebaseUserAuthID(): String?

    fun signWithEmailAndPassword(email: String, password: String): Task<AuthResult>

    fun createUserWithEmailAndPassword(email: String, password: String): Task<AuthResult>

    fun sendPasswordResetEmail(email: String): Task<Void>

}