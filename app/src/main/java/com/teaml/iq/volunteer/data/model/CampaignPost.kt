package com.teaml.iq.volunteer.data.model

import java.util.*

/**
 * Created by Mahmood Ali on 09/02/2018.
 */
data class CampaignPost(
        val campaignId: String,
        val title: String,
        val groupId: String,
        val lastModificationDate: Date,
        val groupLogoImg: String,
        val groupName: String,
        val uploadDate: Date,
        val coverImgName: String)