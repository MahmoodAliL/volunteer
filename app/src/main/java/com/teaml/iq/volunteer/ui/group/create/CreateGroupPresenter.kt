package com.teaml.iq.volunteer.ui.group.create

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.theartofdev.edmodo.cropper.CropImage
import javax.inject.Inject

/**
 * Created by ali on 2/23/2018.
 */
open class CreateGroupPresenter<V : CreateGroupMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), CreateGroupMvpPresenter<V> {

    companion object {

        val TAG: String = CreateGroupPresenter::class.java.simpleName

        const val READ_EXTERNAL_STORAGE_PERMISSION = Manifest.permission.READ_EXTERNAL_STORAGE
        const val READ_EXTERNAL_STORAGE_REQUEST_CODE = 0
        const val LOGO_IMG = 0
        const val COVER_IMG = 1
    }

    protected var groupInfo: HashMap<String, Any> = HashMap()


    protected var imgLogoUri: Uri? = null
    protected var imgCoverUri: Uri? = null
    private var imgFor = 0

    override fun onAttach(mvpView: V) {
        super.onAttach(mvpView)
        Log.d(TAG, "onAttach View")

    }

    protected open fun initGroupInfo(name: String, bio: String) {
        groupInfo.putAll(hashMapOf(
                FbGroup::name.name to name,
                FbGroup::bio.name to bio,
                FbGroup::campaignsNum.name to 0,
                FbGroup::lastModificationDate.name to FieldValue.serverTimestamp(),
                FbGroup::joinDate.name to FieldValue.serverTimestamp()))
    }

    override fun onActionDoneClick(name: String, bio: String) {

        mvpView?.let { view ->
            if (name.isEmpty()) {
                view.onError(R.string.empty_name)
                return
            }

            if (bio.isEmpty()) {
                view.onError(R.string.empty_name)
                return
            }

            initGroupInfo(name, bio)

            if (imgLogoUri == null ) {
                mvpView?.onError(R.string.please_select_img)
                return
            }

            // TODO: check if image is uploaded then does't uploaded
            if (imgCoverUri != null) {
                uploadCoverImg()
                return
            }

            mvpView?.showLoading()
            uploadLogoImg()

        }
    }

    protected fun uploadCoverImg() {

        val uid = dataManager.getFirebaseUserAuthID()

        if (uid == null) {
            mvpView?.onError(R.string.some_error)
            return
        }

        imgCoverUri?.let {
            mvpView?.showLoading()
            dataManager.uploadGroupCoverImg(it).addOnCompleteListener {

                if (it.isSuccessful) {
                    groupInfo[FbGroup::coverImg.name] = uid
                    // upload logo image
                    uploadLogoImg()
                } else {
                    Log.d(TAG, "error cover uploading ", it.exception)
                    mvpView?.onError("${it.exception?.message}")
                    mvpView?.hideLoading()
                }
            }
        }
    }

    protected fun uploadLogoImg() {
        val uid = dataManager.getFirebaseUserAuthID()

        if (uid == null) {
            mvpView?.onError(R.string.some_error)
            return
        }

        Log.e(TAG, "uploadLogoImg")

        if (imgLogoUri != null) {
            Log.e(TAG, "uploadLogoImg in imgLogoUri")
            dataManager.uploadGroupLogoImg(imgLogoUri!!).addOnCompleteListener {

                if (it.isSuccessful) {
                    groupInfo[FbGroup::logoImg.name] = uid
                    uploadOtherInfo()
                } else {
                    Log.e(TAG, "error logo uploading ", it.exception)
                    mvpView?.onError("${it.exception?.message}")
                    mvpView?.hideLoading()
                }
            }
        } else {
            uploadOtherInfo()
        }

    }

    protected fun uploadOtherInfo() {

        mvpView?.getBaseActivity()?.let { activity ->
            dataManager.saveGroupInfo(groupInfo).addOnCompleteListener(activity) {

                mvpView?.hideLoading()

                if (it.isSuccessful) {
                   onUploadSuccess()
                } else {
                    Log.e(TAG, "error uploading other info ", it.exception)
                    mvpView?.onError("${it.exception?.message}")
                }
            }

        }
    }


    protected open fun onUploadSuccess() {
        val uid = dataManager.getFirebaseUserAuthID()

        if (uid == null) {
            mvpView?.onError(R.string.some_error)
            return
        }

        dataManager.setHasGroup(true)
        mvpView?.showGroupDetailFragment(uid)
    }


    private fun readExternalStoragePermissionCheck(): Boolean {
        mvpView?.getBaseActivity()?.let {
            return when {
                it.hasPermission(READ_EXTERNAL_STORAGE_PERMISSION) -> {
                    true
                }
                !it.shouldShowRequestPermissionRationale(READ_EXTERNAL_STORAGE_PERMISSION) -> {
                    mvpView?.showReadExternalStorageRationale()
                    false
                }
                else -> {
                    onRequestReadExternalStoragePermission()
                    false
                }
            }
        }
        return false
    }

    override fun onRequestReadExternalStoragePermission() {
        mvpView?.getBaseFragment()?.requestPermissionsSafely(
                arrayOf(READ_EXTERNAL_STORAGE_PERMISSION), READ_EXTERNAL_STORAGE_REQUEST_CODE)
    }

    override fun onCoverImgClick() {
        imgFor = COVER_IMG
        mvpView?.let { view ->
            if (readExternalStoragePermissionCheck()) {
                view.openCropCoverImg()
            }
        }
    }

    override fun onLogoImgClick() {
        imgFor = LOGO_IMG
        if (readExternalStoragePermissionCheck()) {
            mvpView?.openCropLogoImg()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {

            if (data == null)
                return

            if (resultCode == Activity.RESULT_OK) {

                val imgUri = CropImage.getActivityResult(data).uri
                imgUri?.let {
                    when (imgFor) {

                        LOGO_IMG -> {
                            mvpView?.updateLogoImg(it)
                            imgLogoUri = it
                        }

                        COVER_IMG -> {
                            mvpView?.updateCoverImg(it)
                            imgCoverUri = it
                        }
                       // else -> mvpView?.onError("ماادري شنو من خطا ممكن يصير هنا بس هذا ميقبل الا else اذا اخلي داخل let ")
                    }
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

                    if (imgFor == COVER_IMG) {
                        mvpView?.openCropCoverImg()
                    } else {
                        mvpView?.openCropLogoImg()
                    }

                } else {
                    mvpView?.onError(R.string.permission_deny)
                }
            }
        }
    }

}