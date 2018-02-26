package com.teaml.iq.volunteer.ui.profile.info

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.profile.edit.EditProfileFragment
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.gone
import com.teaml.iq.volunteer.utils.replaceFragmentAndAddToBackStack
import com.teaml.iq.volunteer.utils.visible
import kotlinx.android.synthetic.main.profile_header_layout.*
import kotlinx.android.synthetic.main.profile_info_layout.*
import kotlinx.android.synthetic.main.progressbar_layout.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
class ProfileInfoFragment : BaseFragment(), ProfileInfoMvpView {


    @Inject
    lateinit var mPresenter: ProfileInfoMvpPresenter<ProfileInfoMvpView>

    companion object {
        val TAG: String = ProfileInfoFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = ProfileInfoFragment().apply { arguments  = args }

        const val BUNDLE_KEY_UID = "bundle_key_uid"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }



    override fun onAttach(context: Context?) {
        super.onAttach(context)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return layoutInflater.inflate(R.layout.profile_info_layout,container, false)
    }

    override fun setup(view: View) {
        val uid = arguments?.getString(BUNDLE_KEY_UID, "default uid") ?: "default uid"
        mPresenter.fetchProfileInfo(uid)

    }

    override fun showProgress() {
        progressBarLayout.visible
    }

    override fun hideProgress() {
        progressBarLayout.gone
    }

    override fun showRetryImg() {
        retryImg.visible
    }

    override fun hideRetryImg() {
        retryImg.gone
    }

    override fun updateProfileImg(currentProfileImg: String, lastImgUpdate: String) {
        try {
            val imgRef = FirebaseStorage.getInstance().getReference("${AppConstants.USER_IMG_FOLDER}/$currentProfileImg")
            GlideApp.with(this)
                    .load(imgRef)
                    .signature(ObjectKey(lastImgUpdate))
                    .placeholder(R.drawable.profile_placeholder_img)
                    .circleCrop()
                    .into(profileImg)
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }
    }

    override fun updateUserName(currentUserName: String) {
        txtName.text = currentUserName
    }

    override fun updateUserBio(currentUserBio: String) {
        txtBio.text = currentUserBio
    }

    override fun updateGender(gender: String) {
        txtGender.text = gender
    }

    override fun updateBirthOfDay(currentBirthOfDay: String) {
        txtBirthOfDay.text = currentBirthOfDay
    }

    override fun updatePhoneNumber(currentPhoneNumber: String) {
        txtPhone.text = currentPhoneNumber
    }

    override fun showAndUpdateEmail(email: String) {
        txtEmail.visible
        txtEmail.text = email
    }

    override fun showEditProfileInfo(uid: String) {
        activity?.replaceFragmentAndAddToBackStack(R.id.fragmentContainer, EditProfileFragment.newInstance(), EditProfileFragment.TAG)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        mPresenter.onCreateOptionMenu(menu, inflater)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_edit -> {
                mPresenter.onActionEditClick()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
    }
    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }
}