package com.teaml.iq.volunteer.data.model

import java.util.*

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
class FbUserDetail(
        val img: String = "",
        val name: String = "",
        val bio: String = "",
        val gender: Int = 0,
        val birthOfDay: Date = Date(),
        val email: String = "",
        val phone: String = ""
) {

}