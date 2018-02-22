package com.teaml.iq.volunteer.ui.campaign.members.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestoreException
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.CampaignMembers
import com.teaml.iq.volunteer.ui.campaign.members.CampaignMembersFragment

/**
 * Created by Mahmood Ali on 22/02/2018.
 */

class CampaignMembersAdapterUI(option: FirestoreRecyclerOptions<CampaignMembers>) : FirestoreRecyclerAdapter<CampaignMembers, CampaignMembersVH>(option) {

    private var onDataChanged: (() -> Unit)? = null
    private var onError: ((FirebaseFirestoreException) -> Unit)? = null

    override fun onBindViewHolder(holder: CampaignMembersVH, position: Int, model: CampaignMembers) {
        holder.onBind(model)
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CampaignMembersVH {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.campaign_members_view, parent, false)
        return CampaignMembersVH(v)
    }

    fun setOnDateChangedListener(onDateChanged: () -> Unit) {
        this.onDataChanged = onDateChanged
    }


    override fun onDataChanged() {
        Log.d(CampaignMembersFragment.TAG, "onDateChanged")
        onDataChanged?.invoke()
    }

    fun setOnErrorListener(onError: (FirebaseFirestoreException) -> Unit) {
        this.onError = onError
    }

    override fun onError(e: FirebaseFirestoreException) {
        Log.e(CampaignMembersFragment.TAG, "error on loading image", e)
        onError?.invoke(e)
    }

}
