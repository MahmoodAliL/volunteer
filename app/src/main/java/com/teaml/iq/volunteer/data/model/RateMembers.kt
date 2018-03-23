package com.teaml.iq.volunteer.data.model

import java.util.*

/**
 * Created by ali on 3/16/2018.
 */
data class RateMembers(
    val uid: String,
    val userName: String,
    val imgName: String,
    val joinDate: Date,
    val lastModificationDate: Date,
    val ratingState: Int = 0

)