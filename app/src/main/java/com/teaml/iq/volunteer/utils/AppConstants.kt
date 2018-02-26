package com.teaml.iq.volunteer.utils

/**
 * Created by Mahmood Ali on 31/01/2018.
 */
object AppConstants {

    const val PREF_NAME = "general"

    // time out request
    const val REQUEST_LONG_TIME_OUT = 20L
    const val REQUEST_SHORT_TIME_OUT = 10L

    const val DATE_FORMAT = "yyyy/MM/dd"
    const val TIME_FORMAT = "HH:mm"
    const val TIMESTAMP_FORMAT = "yyyy/MM/dd HH:mm:ss"


    // firestore collection
    const val USERS_COL = "users"
    const val CAMPAIGN_JOINED = "campaigns"
    const val CAMPAIGN_COL = "campaigns"
    const val GROUP_COL = "groups"
    const val MEMBER_COL = "members"

    // query limit
    const val GROUP_CAMPAIGNS_LIMIT = 6L
    const val CAMPAIGN_QUERY_LIMIT = 10L
    const val CAMPAIGN_MEMBERS_LIMIT = 20L
    const val GROUP_QUERY_LIMIT = 20L

    // firestorage
    const val CAMPAIGN_IMG_FOLDER = "campaign img"
    const val USER_IMG_FOLDER = "user img"
    const val GROUP_COVER_IMG_FOLDER = "group cover img"
    const val GROUP_LOGO_IMG_FOLDER = "group logo img"



}