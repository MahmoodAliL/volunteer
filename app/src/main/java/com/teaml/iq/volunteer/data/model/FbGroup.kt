package com.teaml.iq.volunteer.data.model

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
        val joinDate: Date = Date(),
        val lastModificationDate: Date = Date()
) {
    companion object {
        val CAMPAIGNS_NUM = "campaignsNum"
    }
}