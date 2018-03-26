package com.teaml.iq.volunteer.data.firebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.*

/**
 * Created by Mahmood Ali on 07/02/2018.
 */
interface FbFirestore {

    // profile operation
    fun saveProfileInfo(profileInfo: HashMap<String, Any>): Task<Void>

    fun loadProfileInfo(uid: String): Task<DocumentSnapshot>

    fun getUserDocRef(uid: String): DocumentReference


    //TODO: make sure to add index to each query
    // common fun
    fun loadCampaignList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot>

    fun getCampaignDocRef(campaignId: String): DocumentReference

    fun getGroupDocRef(groupId: String): DocumentReference

    fun getCampaignMembersColRef(campaignId: String): CollectionReference
    // campaign fun

    fun loadCampaignMembers(campaignId: String, lastVisibleItem: DocumentSnapshot?): Query

    fun loadRateMembers(campaignId: String, lastVisibleItem: DocumentSnapshot?): Query

    fun onHelpfulRate(campaignId: String, userId: String): Task<Long>

    fun onUnhelpfulRate(campaignId: String, userId: String): Task<Long>

    fun onNotAttendRate(campaignId: String,userId: String): Task<Long>

    fun loadGroupList(lastVisibleItem: DocumentSnapshot?): Task<QuerySnapshot>

    fun loadCampaignUserJoined(uid: String , lastVisibleItem: DocumentSnapshot?): Query

    fun loadFirstTenGroupCampaign(groupId: String): Query

    fun loadGroupCampaignList(groupId: String, lastVisibleItem: DocumentSnapshot?): Query

    fun checkUserJoinWithCampaign(campaignRef: DocumentReference): Task<QuerySnapshot>

    fun addUserToCampaign(campaignRef: DocumentReference, uid: String): Task<Long>

    fun onUserLeaveCampaign(campaignRef: DocumentReference, uid: String): Task<Long>

    fun incrementCampaignView(campaignId: String): Task<Long>

    // group detail

    fun saveGroupInfo(groupInfo: HashMap<String, Any>): Task<Void>

    fun saveCampaignInfo(campaignInfo: HashMap<String, Any>): Task<Transaction>



}