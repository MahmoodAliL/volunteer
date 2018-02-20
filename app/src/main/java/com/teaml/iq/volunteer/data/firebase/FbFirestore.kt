package com.teaml.iq.volunteer.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot

/**
 * Created by Mahmood Ali on 07/02/2018.
 */
interface FbFirestore {

    fun saveProfileInfo(profileInfo: HashMap<String, Any>): Task<Void>

    fun loadProfileInfo(uid: String): Task<DocumentSnapshot>

    fun getUserReference(uid: String): DocumentReference


    //TODO: make sure to add index to each query
    fun loadCampaignList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot>

    fun getCampaignReference(campaignId: String): DocumentReference

    fun getGroupReference(groupId: String): DocumentReference

    fun loadGroupList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot>

    fun loadCampaignUserJoined(uid: String , lastVisibleItem: DocumentSnapshot?): Query

    fun checkUserJoinWithCampaign(campaignRef: DocumentReference): Task<QuerySnapshot>

    fun addUserToCampaign(campaignRef: DocumentReference, uid: String): Task<DocumentSnapshot>

    fun onUserLeaveCampaign(campaignRef: DocumentReference, uid: String): Task<DocumentSnapshot>


}