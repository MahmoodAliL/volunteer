package com.teaml.iq.volunteer.data.firebase

import android.net.Uri
import com.google.firebase.storage.UploadTask

/**
 * Created by Mahmood Ali on 14/02/2018.
 */
interface FbStorage {

    fun uploadProfileImg(uri: Uri): UploadTask

    fun uploadGroupLogoImg(imgUri: Uri): UploadTask

    fun uploadGroupCoverImg(imgUri: Uri): UploadTask
}