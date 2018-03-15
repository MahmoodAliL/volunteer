package com.teaml.iq.volunteer.data.model

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * Created by Mahmood Ali on 09/02/2018.
 */
data class FbCampaign(
        var id: String = "",
        val imgName: String = "",
        val lastModificationDate: Date = Date(),
        val title: String = "",
        val groupRef: DocumentReference = FirebaseFirestore.getInstance().document("group/team-l"),
        val location: GeoPoint = GeoPoint(33.33, 44.33),
        @ServerTimestamp val uploadDate: Date = Date(),
        val startDate: Date = Date(),
        val currentMemberCount: Int = 0,
        val maxMemberCount: Int = 0,
        val description: String = "",
        val age: Int = 0,
        val gender: Int = 0,
        val isEdited: Boolean = false
) {
    companion object {
       const val UPLOAD_DATE = "uploadDate"
       const val START_DATE = "startDate"
    }
}