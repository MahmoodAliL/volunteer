package com.teaml.iq.volunteer.ui.profile.edit

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.CommonUtils
import com.teaml.iq.volunteer.utils.toDateString
import com.teaml.iq.volunteer.utils.toTimestampString
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_edit_profile.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.design.indefiniteSnackbar
import java.util.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 14/02/2018.
 */
class EditProfileFragment : BaseFragment(), EditProfileMvpView {


    init {
        setHasOptionsMenu(true)
    }

    companion object {
        val TAG: String = EditProfileFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = EditProfileFragment().apply { arguments = args }
    }

    private var mBirthOfDate = Date()
    private lateinit var mDatePickerDialog: DatePickerDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    @Inject
    lateinit var mPresenter: EditProfileMvpPresenter<EditProfileMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return layoutInflater.inflate(R.layout.fragment_edit_profile, container, false)
    }


    override fun setup(view: View) {

        mDatePickerDialog = DatePickerDialog(context, { _, year, month, dayOfMonth ->

            mBirthOfDate = CommonUtils.dateFrom(year, month, dayOfMonth)
            birthOfDayField.setText(mBirthOfDate.toDateString())

        }, 1999, 9, 9)

        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        // only user greater than  16 old is accepted
        calendar.set(Calendar.YEAR, currentYear - 16)
        val maxDate = calendar.timeInMillis


        // only user smaller then 100 year is accepted
        calendar.set(Calendar.YEAR, currentYear - 100)
        val minDate = calendar.timeInMillis

        mDatePickerDialog.datePicker.minDate = minDate
        mDatePickerDialog.datePicker.maxDate = maxDate

        mPresenter.fetchProfileInfo()

        birthOfDayField.setOnClickListener { mDatePickerDialog.show() }
        profileImgView.setOnClickListener { mPresenter.onProfileImgClick() }

    }


    override fun openCropImage() {
        context?.let {
            CropImage.activity()
                    .setAspectRatio(1, 1)
                    .setRequestedSize(300, 300)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .start(it, this)
        }
    }

    override fun getBaseFragment(): BaseFragment = this


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun showReadExternalStorageRationale() {
        activity?.alert(R.string.read_external_storage_rationale_for_select_image , null, {
            positiveButton(R.string.ok) {
                mPresenter.onRequestReadExternalStoragePermissionAfterRationale()
            }
            negativeButton(R.string.cancel) { }
        })?.show()
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.e(TAG, "onRequestPermissionsResult")
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onFetchProfileInfoError(msg: String) {
        val retryString = getString(R.string.retry)
        indefiniteSnackbar(profileImgView, msg, retryString) { mPresenter.fetchProfileInfo() }
    }


    override fun showProfileInfo(profileInfo: FbUserDetail) {

        updateProfileImg(profileInfo.img, profileInfo.lastModificationDate.toTimestampString())

        nameField.setText(profileInfo.name)
        bioField.setText(profileInfo.bio)
        phoneFiled.setText(profileInfo.phone)
        mBirthOfDate = profileInfo.birthOfDay
        birthOfDayField.setText(mBirthOfDate.toDateString())

    }

    override fun updateProfileImg(imgName: String, lastModificationDate: String) {

        try {
            val imgRef = FirebaseStorage.getInstance().getReference("${AppConstants.USER_IMG_FOLDER}/$imgName")
            GlideApp.with(this)
                    .load(imgRef)
                    .signature(ObjectKey(lastModificationDate))
                    .circleCrop()
                    .placeholder(R.drawable.profile_placeholder_img)
                    .into(profileImgView)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

    }

    override fun updateProfileImg(uri: Uri) {
        GlideApp.with(this)
                .load(uri)
                .circleCrop()
                .placeholder(R.drawable.profile_placeholder_img)
                .into(profileImgView)
    }

    override fun showEmail(email: String) {
        emailField.setText(email)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_done -> {

                val name = nameField.text.toString()
                val bio = bioField.text.toString()
                val phoneNumber = phoneFiled.text.toString()

                mPresenter.onActionDoneClick(name, bio, phoneNumber, mBirthOfDate)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProfileInfoFragment() {
        activity?.onBackPressed()
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}