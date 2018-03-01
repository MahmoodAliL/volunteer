package com.teaml.iq.volunteer.data.model

import java.util.*

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
data class GroupInfo(
        val id: String,
        val groupImg: String,
        val name: String,
        val memberNumber: Int,
        val campaignsNum: Int,
        val lastModificationDate: Date
)