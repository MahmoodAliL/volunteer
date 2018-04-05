package com.teaml.iq.volunteer.data.model

import com.google.firebase.firestore.ServerTimestamp
import java.util.*

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
data class FbUserDetail(
        val img: String = "",
        @ServerTimestamp val lastModificationDate: Date = Date(),
        val name: String = "",
        val myGroupId: String =  "",
        val bio: String = "Hi there i am using volunteer iq app ",
        val gender: Int = 0,
        val birthOfDay: Date = Date(),
        val email: String = "",
        val phone: String = "",
        val campaignJoinCount: Int = 0,
        val xpPoint: Int = 0,
        val helpfulCount: Int = 0,
        val unhelpfulCount: Int = 0,
        val notAttendCount: Int = 0
) {
    companion object {
        const val NAME = "name"
        const val IMG = "img"
        const val LAST_IMG_UPDATE = "lastModificationDate"
        const val BIO = "bio"
        const val GENDER = "gender"
        const val EMAIL = "email"
        const val PHONE = "phone"
    }

    // using reflection or we can using name specified in companion object
    // which one is best ?
    // في رئي استعمال الرفلكشن افضل لانة اذا حدث تغير في اسم المتغير فلن نقوم اي شي لان اسم المتغير سوف يتم اخذ اسم التغير من المتغير نفسة
    fun toHashMap() = hashMapOf(FbUserDetail::img.name to img,
            FbUserDetail::lastModificationDate.name to lastModificationDate,
            FbUserDetail::name.name to name,
            FbUserDetail::bio.name to bio,
            FbUserDetail::gender.name to gender,
            FbUserDetail::birthOfDay.name to birthOfDay,
            FbUserDetail::email.name to email,
            FbUserDetail::phone .name to phone,
            FbUserDetail::xpPoint.name  to xpPoint,
            FbUserDetail::campaignJoinCount.name to campaignJoinCount,
            FbUserDetail::helpfulCount.name to helpfulCount,
            FbUserDetail::unhelpfulCount.name to unhelpfulCount,
            FbUserDetail::notAttendCount.name to notAttendCount
    )

}