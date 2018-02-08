package com.teaml.iq.volunteer.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

/**
 * Created by Mahmood Ali on 07/02/2018.
 */
interface FbFirestore {
    fun saveBasicUserInfo(basicUserInfo: HashMap<String, Any>): Task<Void>

    fun loadProfileInfo(): Task<DocumentSnapshot>
}