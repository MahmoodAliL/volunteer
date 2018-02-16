package com.teaml.iq.volunteer.ui.campaign.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.utils.*
import kotlinx.android.synthetic.main.campaign_detail_layout.*
import kotlinx.android.synthetic.main.progressbar_layout.*
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
            mPresenter.loadCampaignDetail(campaignId, groupId)
        }
    }


    override fun showCampaignDetail(campaign: FbCampaign, group: FbGroup) {

        hideDetailLayout.gone
        btnJoin.visible

        // campaign detail
        txtTitle.text = campaign.title
        txtStartDate.text = campaign.startDate.toDateString()
        txtStartTime.text = campaign.startDate.toTimeString()
        txtMember.text = "33 Member"

        txtGroupName.text = group.name

        // loading image
        try {
            val groupImgRef = FirebaseStorage.getInstance()
                    .getReference("${AppConstants.GROUP_LOGO_IMG_FOLDER}/${group.logoImg}")

            GlideApp.with(this)
                    .load(groupImgRef)
                    .circleCrop()
                    .placeholder(R.drawable.org_placeholder_img)
                    .into(groupImg)

            val campaignImg = FirebaseStorage.getInstance()
                    .getReference("${AppConstants.CAMPAIGN_IMG_FOLDER}/${campaign.imgName}")

            GlideApp.with(this)
                    .load(campaignImg)
                    .placeholder(R.drawable.campaign_placeholder_img)
                    .into(campaignCoverImg)

        } catch (e: Exception) {
            Log.e(TAG, "on loading img error", e)
        }

        txtDescription.text = campaign.description

        var gender = "--"
        context?.let {
            gender = CommonUtils.intGenderToString(campaign.age, it)
        }
        txtRequirement.text = getString(R.string.campaign_requirement, gender, campaign.age)
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