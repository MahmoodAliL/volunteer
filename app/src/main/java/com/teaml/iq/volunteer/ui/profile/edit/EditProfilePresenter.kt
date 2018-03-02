package com.teaml.iq.volunteer.ui.profile.edit

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.ui.profile.info.ProfileInfoPresenter
import com.theartofdev.edmodo.cropper.CropImage
import java.util.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 14/02/2018.
 */
class EditProfilePresenter<V : EditProfileMvpView> @Inject constructor(dataManager: DataManager) : BasePresenter<V>(dataManager), EditProfileMvpPresenter<V> {


    companion object {
        val TAG: String = EditProfilePresenter::class.java.simpleName

        const val READ_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
        const val RC_READ_EXTERNAL_STORAGE = 0

    }


    private var isFieldLoadProfileInfo = true
    private var profileImgUri: Uri? = null

    override fun fetchProfileInfo() {
        mvpView?.let { view ->


            view.getBaseActivity()?.let { activity ->

                val uid = dataManager.getFirebaseUserAuthID()
                // if user does not exist from some reason
                if (uid == null) {
                    setUserAsLoggedOut()
                    return
                }
                view.showLoading()
                dataManager.loadProfileInfo(uid).addOnCompleteListener(activity) { task ->

                    if (task.isSuccessful) {

                        if (mvpView == null)
                            return@addOnCompleteListener
                        view.hideLoading()

                        isFieldLoadProfileInfo = false

                        val profileInfo = task.result.toObject(FbUserDetail::class.java)
                        dataManager.getCurrentUserEmail()?.let {
                            view.showEmail(it)
                        }

                        view.showProfileInfo(profileInfo)
                    } else {
                        Log.e(ProfileInfoPresenter.TAG, "${task.exception?.message}")
                        isFieldLoadProfileInfo = true
                        view.onFetchProfileInfoError("${task.exception?.message}")
                    }

                }
            }
        }
    }


    override fun onActionDoneClick(name: String, bio: String, phoneNumber: String, birthOfDay: Date) {
        if (isFieldLoadProfileInfo)
            return

        mvpView?.let { view ->

            if (name.isEmpty()) {
                view.onError(R.string.empty_name)
                return
            }

            view.hideKeyboard()

            view.getBaseActivity()?.let { activity ->

                val uid = dataManager.getFirebaseUserAuthID()

                if (uid == null) {
                    setUserAsLoggedOut()
                    return
                }

                view.showLoading(R.string.saving)

                val profileInfo = hashMapOf(FbUserDetail::name.name to name,
                        FbUserDetail::lastModificationDate.name to FieldValue.serverTimestamp(),
                        FbUserDetail::bio.name to bio, FbUserDetail::phone.name to phoneNumber,
                        FbUserDetail::birthOfDay.name to birthOfDay, FbUserDetail::img.name to uid)

                dataManager.saveProfileInfo(profileInfo).addOnCompleteListener(activity) {
                    if (it.isSuccessful) {

                        if (mvpView == null)
                            return@addOnCompleteListener

                        if (profileImgUri != null) {
                            Log.d(TAG, "start uploading image")
                            uploadProfileImg()
                        } else {
                            Log.d(TAG, "save info without image")
                            mvpView?.hideLoading()
                            mvpView?.showProfileInfoFragment()
                        }

                    } else {
                        mvpView?.hideLoading()
                        mvpView?.onError("${it.exception?.message}")
                    }
                }

            }
        }
    }

    private fun uploadProfileImg() {
        profileImgUri?.let {
            dataManager.uploadProfileImg(it).addOnCompleteListener {

                mvpView?.hideLoading()
                if (it.isSuccessful) {
                    Log.d(TAG, "uploading image successfully")
                    mvpView?.showProfileInfoFragment()
                } else {
                    Log.d(TAG,  "error uploading ", it.exception)
                    mvpView?.onError("${it.exception?.message}")
                }
            }
        }

    }

    override fun onProfileImgClick() {

        mvpView?.getBaseActivity()?.let { activity ->
            Log.e(TAG, "onProfileImgClick")
            when {
                // check permission
                activity.hasPermission(READ_EXTERNAL_STORAGE_PERMISSION) -> mvpView?.openCropImage()
                // should should show request permission rationale
                !activity.shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE_PERMISSION) ->
                    mvpView?.showReadExternalStorageRationale()

                // request permission
                else -> onRequestReadExternalStoragePermissionAfterRationale()

            }

        }
    }


    override fun onRequestReadExternalStoragePermissionAfterRationale() {
        mvpView?.getBaseFragment()?.requestPermissionsSafely(
                arrayOf(READ_EXTERNAL_STORAGE_PERMISSION),
                RC_READ_EXTERNAL_STORAGE
        )
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {



        when (requestCode) {

            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {

                // if user does not select image
                if (data == null)
                    return
                val result = CropImage.getActivityResult(data)

                if (resultCode == Activity.RESULT_OK) {
                    profileImgUri = result.uri

                    profileImgUri?.let {
                        mvpView?.updateProfileImg(it)
                    }

                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    mvpView?.onError("${result.error.message}")
                }

            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        Log.d(TAG,"onRequestPermissionResult : $requestCode")

        when (requestCode) {

            RC_READ_EXTERNAL_STORAGE -> {
                Log.e(TAG, "on read external storage")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mvpView?.openCropImage()
                } else {
                    mvpView?.onError(R.string.permission_deny)
                }
            }
        }
    }


}