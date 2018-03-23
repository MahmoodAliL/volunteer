package com.teaml.iq.volunteer.ui.campaign.detail

import android.graphics.Color
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.account.AccountActivity
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import com.teaml.iq.volunteer.ui.campaign.map.MapFragment
import com.teaml.iq.volunteer.ui.campaign.members.CampaignMembersFragment
import com.teaml.iq.volunteer.ui.campaign.members.rate.RateMemberFragment
import com.teaml.iq.volunteer.ui.group.GroupActivity
import com.teaml.iq.volunteer.utils.*
import kotlinx.android.synthetic.main.campaign_detail_layout.*
import kotlinx.android.synthetic.main.progressbar_layout.*
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
class CampaignDetailFragment : BaseFragment(), CampaignDetailMvpView {


    companion object {
        val TAG: String = CampaignDetailFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = CampaignDetailFragment().apply { arguments = args }

        const val BUNDLE_KEY_GROUP_ID = "bundle_key_group_id"
        const val BUNDLE_KEY_CAMPAIGN_ID = "bundle_key_campaign_id"
    }

    @Inject
    lateinit var mPresenter: CampaignDetailMvpPresenter<CampaignDetailMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Campaign Detail"

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return layoutInflater.inflate(R.layout.campaign_detail_layout, container, false)
    }


    override fun setup(view: View) {

        arguments?.let {
            val groupId = it.getString(BUNDLE_KEY_GROUP_ID)
            val campaignId = it.getString(BUNDLE_KEY_CAMPAIGN_ID)
            mPresenter.prepareLoadCampaign(campaignId, groupId)


            groupImg.setOnClickListener { mPresenter.onGroupImgClick() }
            retryImg.setOnClickListener { mPresenter.onRetryImgClick() }
            btnJoin.setOnClickListener { mPresenter.onJoinClick() }
            txtLocation.setOnClickListener { mPresenter.onOpenMapClick() }
            txtMember.setOnClickListener { mPresenter.onMembersClick() }
        }
    }

    override fun openGroupActivity(groupId: String) {
        context?.startActivity<GroupActivity>(CampaignActivity.EXTRA_KEY_GROUP_ID to groupId)
    }

    override fun onUserOwnerCampaign() {
        Log.d(TAG, "onUserOwnerCampaign")
    }

    override fun showRateMembersFragment(campaignId: String) {
        Log.d(TAG, "showRateMembersFragment")
    }

    /**
     * for more info please  visit  https://developers.google.com/maps/documentation/urls/android-intents#uriencoding
     */
    override fun openGoogleMap(geoPoint: GeoPoint) {

        val bundle = bundleOf(
                MapFragment.BUNDLE_KEY_LATITUDE to geoPoint.latitude,
                MapFragment.BUNDLE_KEY_LONGITUDE to geoPoint.longitude
        )
        activity?.addFragmentAndAddToBackStack(
                R.id.fragmentContainer,
                MapFragment.newInstance(bundle),
                MapFragment.TAG
        )
        /* Log.e(TAG, "open google map clicked")

         val gmUri = Uri.parse("geo:0,0?q=${geoPoint.latitude},${geoPoint.longitude}(campaign place)?z=10")
         val mapIntent = Intent(Intent.ACTION_VIEW, gmUri)
         mapIntent.`package` = "com.google.android.apps.maps"
         context?.let {
             if (mapIntent.resolveActivity(it.packageManager) != null) {
                 startActivity(mapIntent)
             } else {
                 showMessage("google map not install ")
                 it.browse("https://www.google.iq/maps/place/${geoPoint.latitude}+${geoPoint.longitude}/10z?hl=ar", true)
             }
         }*/
    }

    override fun updateJoinBtnToJoin() {
        btnJoin.setBackgroundResource(R.drawable.btn_join_background)
        btnJoin.text = getString(R.string.join)
    }

    override fun updateJoinBtnToLeave() {
        btnJoin.setBackgroundResource(R.drawable.btn_leave_background)
        btnJoin.text = getString(R.string.leave)
    }

    override fun updateJoinBtnToRateMember() {
        btnJoin.setText(R.string.rate_member)
    }

    override fun disableJoinBtn(@StringRes btnText: Int ,  @StringRes note: Int) {
        btnJoin.setText(btnText)
        btnJoin.setBackgroundResource(R.drawable.btn_join_background)
        btnJoin.isEnabled = false
        txtNote.setTextColor(Color.RED)
        txtNote.text = getString(note)

    }

    override fun showCampaignDetail(campaign: FbCampaign, group: FbGroup) {

        hideDetailLayout.gone
        btnJoin.visible

        // campaign detail
        txtCampaignTitle.text = campaign.title
        txtStartDate.text = campaign.startDate.toDateString()
        txtStartTime.text = campaign.startDate.toTimeString()
        txtMember.text = getString(R.string.campaign_member, campaign.currentMemberCount)

        txtGroupName.text = group.name
        txtPublishDate.text = getString(R.string.publish_date, campaign.uploadDate.toDateString())

        // loading image
        try {
            val groupImgRef = FirebaseStorage.getInstance()
                    .getReference("${AppConstants.GROUP_LOGO_IMG_FOLDER}/${group.logoImg}")

            GlideApp.with(this)
                    .load(groupImgRef)
                    .circleCrop()
                    .signature(ObjectKey(group.lastModificationDate))
                    .placeholder(R.drawable.group_logo_placeholder_img)
                    .into(groupImg)

            val campaignImg = FirebaseStorage.getInstance()
                    .getReference("${AppConstants.CAMPAIGN_IMG_FOLDER}/${campaign.imgName}")

            GlideApp.with(this)
                    .load(campaignImg)
                    .signature(ObjectKey(campaign.lastModificationDate))
                    .placeholder(R.drawable.campaign_placeholder_img)
                    .into(campaignCoverImg)

        } catch (e: Exception) {
            Log.e(TAG, "on loading img error", e)
        }


        var gender = "--"
        context?.let { gender = CommonUtils.intGenderToString(campaign.gender, it) }
        txtRequirement.text = getString(R.string.campaign_requirement, campaign.maxMemberCount, gender, campaign.age)
    }

    override fun updateCurrentMembers(currentMembers: Long) {
        txtMember.text = getString(R.string.campaign_member, currentMembers)
    }



    override fun updateCampaignDetail(campaign: FbCampaign) {
        // campaign detail
        txtCampaignTitle.text = campaign.title
        txtStartDate.text = campaign.startDate.toDateString()
        txtStartTime.text = campaign.startDate.toTimeString()
        txtDescription.text = campaign.description
        txtMember.text = getString(R.string.campaign_member, campaign.currentMemberCount)

    }


    override fun openSignInActivity() {
        context?.startActivity<AccountActivity>()
    }

    override fun showCampaignMembersFragment(campaignId: String) {

        val bundle = Bundle()
        bundle.putString(BUNDLE_KEY_CAMPAIGN_ID, campaignId)

        activity?.addFragmentAndAddToBackStack(
                R.id.fragmentContainer,
                RateMemberFragment.newInstance(bundle),
                CampaignMembersFragment.TAG
        )
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

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}