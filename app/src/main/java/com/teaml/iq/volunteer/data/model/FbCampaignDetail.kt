package com.teaml.iq.volunteer.data.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

/**
 * Created by Mahmood Ali on 09/02/2018.
 */
data class FbCampaignDetail(
        val id: String = "",
        val imgName: String = "",
        val title: String = "",
        val groupRef: DocumentReference = FirebaseFirestore.getInstance().document("group/team-l"),
        val location: String = "",
        val uploadDate: Date = Date(),
        val startDate: Date = Date(),
        val description: String = "",
        val age: Int = 0,
        val gender: Int = 0
)