package com.teaml.iq.volunteer.ui.profile.info

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.utils.*
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

    override fun showProfileInfo(profileInfo: FbUserDetail) {

        context?.let { context ->

            with(profileInfo) {
                txtName.text = name
                txtGender.text = CommonUtils.intGenderToString(gender, context)
                txtBirthDay.text = birthOfDay.toSimpleString()

                if (!phone.isEmpty())
                    txtPhone.text = phone
                if (!email.isEmpty())
                    txtEmail.text = email
                if (!bio.isEmpty())
                    txtBio.text = bio
            }

        }


        try {
            val imgRef = FirebaseStorage.getInstance().getReference("${AppConstants.USER_IMG_FOLDER}/${profileInfo.img}")
            GlideApp.with(this)
                    .load(imgRef)
                    .placeholder(R.drawable.profile_placeholder_img)
                    .circleCrop()
                    .into(profileImg)
        } catch (e: Exception) {
            Log.d(TAG, e.message)
        }

    }


}