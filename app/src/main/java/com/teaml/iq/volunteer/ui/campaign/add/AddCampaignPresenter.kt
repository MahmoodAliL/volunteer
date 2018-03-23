package com.teaml.iq.volunteer.ui.campaign.add

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.GeoPoint
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.SelectedDate
import com.teaml.iq.volunteer.data.model.SelectedTime
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.CommonUtils
import com.theartofdev.edmodo.cropper.CropImage
import java.util.*
import javax.inject.Inject

/**
 * Created by ali on 2/25/2018.
 */
open class AddCampaignPresenter<V : AddCampaignMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), AddCampaignMvpPresenter<V> {

    companion object {
        const val READ_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
        const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 0

        val TAG: String = AddCampaignPresenter::class.java.simpleName
    }

    protected var imgUri: Uri? = null
    private val imgName = UUID.randomUUID()
    protected var campaignInfo: HashMap<String, Any> = HashMap()


    override fun onActionDoneClick(name: String, selectedTime: SelectedTime?, selectedDate: SelectedDate?, location: GeoPoint, description: String, gender: DataManager.UserGender, age: Int?, maxMembers: Int?) {

        mvpView?.let { view ->

            val uid = dataManager.getFirebaseUserAuthID()

            if (uid == null) {
                view.showMessage(R.string.some_error)
                return
            }

            if (name.isEmpty()) {
                view.onError(R.string.empty_name)
                return
            }

            if (selectedDate == null) {
                view.onError(R.string.please_select_start_date)
                return
            }

            if (selectedTime == null) {
                view.onError(R.string.please_select_start_time)
                return
            }

            if (description.isEmpty()) {
                view.onError(R.string.empty_description)
                return
            }

            if (age == null) {
                view.onError(R.string.please_select_required_age)
                return
            }

            if (maxMembers == null) {
                view.onError(R.string.please_select_max_count_members)
                return
            }

            if (age !in 10..100) {
                view.onError(R.string.required_age_should_be_in_range_10_100)
                return
            }
            if (maxMembers !in 10..1000) {
                view.onError(R.string.max_count_member_should_be_in_range_10_1000)
                return
            }

            val startDate = with(selectedDate) {
                CommonUtils.dateTimeFrom(year, month, dayOfMonth, selectedTime.hour, selectedTime.minute)
            }

            campaignInfo.putAll(hashMapOf(
                    FbCampaign::title.name to name,
                    FbCampaign::startDate.name to startDate,
                    FbCampaign::uploadDate.name to FieldValue.serverTimestamp(),
                    FbCampaign::lastModificationDate.name to FieldValue.serverTimestamp(),
                    FbCampaign::description.name to description,
                    FbCampaign::gender.name to gender.type,
                    FbCampaign::location.name to location,
                    FbCampaign::age.name to age,
                    FbCampaign::maxMemberCount.name to maxMembers,
                    FbCampaign::currentMemberCount.name to 0,
                    FbCampaign::groupRef.name to dataManager.getGroupDocRef(uid)
            ))

            view.showLoading()
            if (imgUri != null) {
                uploadImg()
                return
            }

            uploadOtherInfo()
        }
    }

    protected fun uploadImg() {

        imgUri?.let {

            dataManager.uploadCampaignImg(it, imgName.toString()).addOnCompleteListener {
                if (it.isSuccessful) {
                    // upload logo image
                    campaignInfo[FbCampaign::imgName.name] = imgName.toString()
                    // upload other information
                    uploadOtherInfo()

                } else {
                    Log.d(TAG, "error img uploading ", it.exception)
                    mvpView?.onError("${it.exception?.message}")
                    mvpView?.hideLoading()
                }
            }
        }
    }

    protected open fun uploadOtherInfo() {
        mvpView?.let { view ->

            dataManager.saveCampaignInfo(campaignInfo).addOnCompleteListener {
                if (it.isSuccessful) {
                    view.hideLoading()
                    view.showMyGroupFragment(dataManager.getFirebaseUserAuthID()!!)
                } else {
                    view.onError(R.string.error_while_upload_campaign)
                    view.hideLoading()
                }
            }
        }
    }


    private fun readExternalStoragePermissionCheck() {
        mvpView?.getBaseActivity()?.let {
            when {
                it.hasPermission(READ_EXTERNAL_STORAGE_PERMISSION) -> {
                    mvpView?.openCropImg()
                }
                it.shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE_PERMISSION) -> {
                    mvpView?.showReadExternalStorageRationale()
                }
                else -> {
                    onRequestReadExternalStoragePermission()
                }
            }
        }
    }

    override fun onRequestReadExternalStoragePermission() {
        mvpView?.getBaseActivity()?.requestPermissionsSafely(
                arrayOf(READ_EXTERNAL_STORAGE_PERMISSION),
                READ_EXTERNAL_STORAGE_REQUEST_CODE
        )
    }

    override fun onImgClick() {
        readExternalStoragePermissionCheck()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (data == null)
                return

            if (resultCode == Activity.RESULT_OK) {

                imgUri = CropImage.getActivityResult(data).uri
                imgUri?.let {
                    mvpView?.updateImg(it)
                }
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
            mvpView?.onError("error in image")
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d("PermissionResult", "$requestCode")
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mvpView?.openCropImg()
                } else {
                    mvpView?.onError(R.string.permission_deny)
                }
            }
        }
    }
}