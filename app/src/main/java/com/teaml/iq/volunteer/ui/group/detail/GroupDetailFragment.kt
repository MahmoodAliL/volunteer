package com.teaml.iq.volunteer.ui.group.detail


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import com.teaml.iq.volunteer.ui.group.view_all_campaign.GroupCampaignsFragment
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.addFragmentAndAddToBackStack
import com.teaml.iq.volunteer.utils.gone
import com.teaml.iq.volunteer.utils.visible
import kotlinx.android.synthetic.main.fragment_group_detail.*
import kotlinx.android.synthetic.main.progressbar_layout.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class GroupDetailFragment : BaseFragment(), GroupDetailMvpView {

    companion object {
        val TAG: String = GroupDetailFragment::class.java.simpleName

        fun newInstance(args: Bundle = Bundle.EMPTY): GroupDetailFragment = GroupDetailFragment().apply { arguments = args }

        const val BUNDLE_KEY_GROUP_ID = "bundle_key_group_id"
    }

    @Inject
    lateinit var mAdapter: GroupCampaignsAdapter

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mPresenter: GroupDetailMvpPresenter<GroupDetailMvpView>

    private var groupId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return inflater.inflate(R.layout.fragment_group_detail, container, false)
    }

    override fun setup(view: View) {
        arguments?.let {
            groupId = it.getString(BUNDLE_KEY_GROUP_ID)
            mPresenter.loadGroupDetail(groupId)

            // val list = (1..9).map { GroupCampaigns("","title of campaign $it","campaign.jpg", "2018/04/0$it" ) }.toMutableList()

            recyclerView.isNestedScrollingEnabled = false
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = mLinearLayoutManager
            recyclerView.adapter = mAdapter

            mAdapter.setOnViewItemClick { campaignId ->
                activity?.startActivity<CampaignActivity>(
                        CampaignActivity.EXTRA_KEY_CAMPAIGN_ID to campaignId,
                        CampaignActivity.EXTRA_KEY_GROUP_ID to groupId
                )
            }

            btnViewAll.setOnClickListener { mPresenter.onViewAllClick() }

        }
    }


    override fun showGroupDetail(fbGroup: FbGroup) {

        hideDetailLayout.gone

        txtGroupName.text = fbGroup.name
        txtGroupBio.text = fbGroup.bio
        txtCampaignsNum.text = getString(R.string.campaigns_number, fbGroup.campaignsNum)

        val groupLogoRef = FirebaseStorage.getInstance()
                .getReference("${AppConstants.GROUP_LOGO_IMG_FOLDER}/${fbGroup.logoImg}")

        GlideApp.with(this)
                .load(groupLogoRef)
                .placeholder(R.color.image_border)
                .into(logoImgView)

        val groupCoverImgRef = FirebaseStorage.getInstance()
                .getReference("${AppConstants.GROUP_COVER_IMG_FOLDER}/${fbGroup.coverImg}")

        GlideApp.with(this)
                .load(groupCoverImgRef)
                .placeholder(R.drawable.campaign_placeholder_img)
                .into(groupCoverImgView)

    }

    override fun showGroupCampaignsFragment() {

        if (groupId.isEmpty()) {
            Log.e(TAG, "groupId is empty !!!")
            onError(R.string.some_error)

        } else {
            val bunlde = Bundle()
            bunlde.putString(GroupCampaignsFragment.BUNDLE_KEY_GROUP_ID , groupId)
            activity?.addFragmentAndAddToBackStack(
                    R.id.fragmentContainer,
                    GroupCampaignsFragment.newInstance(bunlde),
                    GroupCampaignsFragment.TAG
            )
        }

    }

    override fun updateCampaign(list: MutableList<GroupCampaigns>) {
        mAdapter.addItems(list)
    }

    override fun showViewAll() {
        btnViewAll.visible
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
        super.onDestroyView()
        mPresenter.onDetach()
    }

}// Required empty public constructor
