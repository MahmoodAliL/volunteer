package com.teaml.iq.volunteer.ui.group.view_all_campaign

import android.os.Bundle
import android.view.View
import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataFragment
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import com.teaml.iq.volunteer.ui.group.detail.GroupCampaignsAdapter
import kotlinx.android.synthetic.main.recycler_view_layout.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by ali on 2/21/2018.
 */
class GroupCampaignsFragment : BaseLoadDataFragment<GroupCampaigns>(), GroupCampaignsMvpView {


    companion object {
        val TAG: String = GroupCampaignsFragment::class.java.simpleName

        fun newInstance(args: Bundle = Bundle.EMPTY): GroupCampaignsFragment = GroupCampaignsFragment().apply { arguments = args }
        const val BUNDLE_KEY_GROUP_ID = "bundle_key_group_id"
    }

    @Inject
    lateinit var mPresenter: GroupCampaignsMvpPresenter<GroupCampaignsMvpView>

    @Inject
    lateinit var mGroupCampaignsAdapter: GroupCampaignsAdapter

    private var groupId = "defaultGroup"

    override fun onCreateRecyclerAdapter(): BaseRecyclerAdapter<GroupCampaigns> {
        return mGroupCampaignsAdapter
    }

    override fun initActivityComponent() {
        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }
    }

    override fun setup(view: View) {

        mGroupCampaignsAdapter.setOnLoadMoreListener { mPresenter.onLoadMore() }
        mGroupCampaignsAdapter.setOnViewItemClick { campaignId ->
            activity?.startActivity<CampaignActivity>(
                    CampaignActivity.EXTRA_KEY_GROUP_ID to groupId,
                    CampaignActivity.EXTRA_KEY_CAMPAIGN_ID to campaignId
            )
        }

        retryImg.setOnClickListener { mPresenter.onRetryClick() }
        swipeRefreshLayout.setOnRefreshListener { mPresenter.onSwipeRefresh() }

        arguments?.let {
            groupId = it.getString(BUNDLE_KEY_GROUP_ID)
            mPresenter.onViewPrepared(groupId)
        }


    }

    override fun updateCampaign(campaigns: List<GroupCampaigns>) {
        mGroupCampaignsAdapter.addItems(campaigns)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDetach()
    }

}