package com.teaml.iq.volunteer.ui.main.myaccount


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.account.AccountActivity
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import com.teaml.iq.volunteer.ui.group.GroupActivity
import com.teaml.iq.volunteer.ui.profile.ProfileActivity
import com.teaml.iq.volunteer.utils.AppConstants
import kotlinx.android.synthetic.main.myaccount_layout.*
import kotlinx.android.synthetic.main.myaccount_layout_not_sign_in.*
import kotlinx.android.synthetic.main.profile_header_layout.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class MyAccountFragment : BaseFragment(), MyAccountMvpView {


    @Inject
    lateinit var mPresenter: MyAccountMvpPresenter<MyAccountMvpView>

    companion object {
        val TAG: String = MyAccountFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = MyAccountFragment().apply { arguments = args }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        var layout = R.layout.myaccount_layout_not_sign_in


        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
            // decideCurrentLayout()
            layout = mPresenter.decideCurrentLayout()
        }

        return inflater.inflate(layout, container, false)
    }

    override fun setup(view: View) {
        mPresenter.onViewPrepared()

    }

    override fun openSignInActivity() {
        activity?.startActivity<AccountActivity>()
    }

    override fun setupViewWithSignOutStatus() {
        btnSignIn.setOnClickListener { mPresenter.onSignInClick() }
    }

    override fun setupViewWithSignInStatus() {
        signOut.setOnClickListener { mPresenter.setUserAsLoggedOut() }
        myProfile.setOnClickListener { mPresenter.onMyProfileClick() }
        myGroup.setOnClickListener { mPresenter.onMyGroupClick() }
        mPresenter.fetchProfileInfo()
    }

    override fun onResume() {
        super.onResume()
        mPresenter.fetchProfileInfo()
    }

    override fun showProfileInfo(profileInfo: FbUserDetail) {


        txtName.text = profileInfo.name
        if (!profileInfo.bio.isEmpty())
            txtBio.text = profileInfo.bio
        else
            txtBio.text = "-----"

        try {
            val imgRef = FirebaseStorage.getInstance().getReference("${AppConstants.USER_IMG_FOLDER}/${profileInfo.img}")
            GlideApp.with(this)
                    .load(imgRef)
                    .signature(ObjectKey(profileInfo.lastModificationDate))
                    .circleCrop()
                    .placeholder(R.drawable.profile_placeholder_img)
                    .into(profileImg)
        } catch (e: Exception) {
            Log.e(TAG, e.message)
        }

    }

    override fun openProfileActivity(uid: String) {
        activity?.startActivity<ProfileActivity>(ProfileActivity.EXTRA_KEY_UID to uid)
    }

    override fun openGroupActivityWithCreateGroup() {
        activity?.startActivity<GroupActivity>(
                GroupActivity.EXTRA_CURRENT_FRAGMENT to GroupActivity.FragmentType.CREATE_GROUP.type
        )
    }

    override fun openGroupActivityWithGroupDetail(groupId: String) {
        activity?.startActivity<GroupActivity>(CampaignActivity.EXTRA_KEY_GROUP_ID to groupId)
    }


}// Required empty public constructor
