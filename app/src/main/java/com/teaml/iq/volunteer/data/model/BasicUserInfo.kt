package com.teaml.iq.volunteer.data.model

import com.teaml.iq.volunteer.data.DataManager
import java.util.*

/**
 * Created by Mahmood Ali on 07/02/2018.
 */
data class BasicUserInfo(
        private val name: String = "",
        private val gender: DataManager.UserGender = DataManager.UserGender.MALE,
        private val birthOfDay: Date = Date()
) {

    companion object {
        val USER_NAME = "userName"
        val BIRTH_OF_DAY = "birthOfDay"
        val GENDER = "gender"
    }

    fun toMap(): HashMap<String, Any> {
        return hashMapOf( USER_NAME to name, GENDER to gender.type, BIRTH_OF_DAY to birthOfDay)
    }


}