package com.teaml.iq.volunteer.data.model

import java.util.*

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
data class GroupCampaigns(
        val campaignId: String,
        val title: String,
        val imgName: String,
        val uploadDate: Date,
        val viewsCount: Int
)