package com.teaml.iq.volunteer.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * Created by Mahmood Ali on 12/02/2018.
 */
data class FbGroup(
        val name: String = "",
        val logoImg: String = "",
        val coverImg: String = "",
        val bio: String = "",
        val userRef: String = "",
        val campaignsNum: Int = 0,
        @ServerTimestamp val joinDate: Date = Date(),
        @ServerTimestamp val lastModificationDate: Date = Date()
) {
    companion object {
        const val CAMPAIGNS_NUM = "campaignsNum"
    }
}