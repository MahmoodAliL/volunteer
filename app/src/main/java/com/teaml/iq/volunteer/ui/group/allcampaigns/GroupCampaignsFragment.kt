package com.teaml.iq.volunteer.ui.group.view_all_campaign

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import com.teaml.iq.volunteer.ui.group.detail.GroupCampaignsAdapter
import com.teaml.iq.volunteer.utils.gone
import com.teaml.iq.volunteer.utils.invisible
import com.teaml.iq.volunteer.utils.visible
import kotlinx.android.synthetic.main.progressbar_layout.*
import kotlinx.android.synthetic.main.recycler_view_layout.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by ali on 2/21/2018.
 */
class GroupCampaignsFragment : BaseFragment(), GroupCampaignsMvpView {


    companion object {
        val TAG = GroupCampaignsFragment::class.java.simpleName

        fun newInstance(args: Bundle = Bundle.EMPTY): GroupCampaignsFragment = GroupCampaignsFragment().apply { arguments = args }
        const val BUNDLE_KEY_GROUP_ID = "bundle_key_group_id"
    }

    @Inject
    lateinit var mPresenter: GroupCampaignsMvpPresenter<GroupCampaignsMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mGroupCampaignsAdapter: GroupCampaignsAdapter

    private var groupId = "defaultGroup"

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?, savedInstanceState: Bundle?): View? {

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return inflater.inflate(R.layout.recycler_view_layout, parent, false)
    }

    override fun setup(view: View) {

        // setup recyclerView
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = mGroupCampaignsAdapter

        mGroupCampaignsAdapter.initRecyclerView(recyclerView)
        mGroupCampaignsAdapter.setOnLoadingMoreListener { mPresenter.onLoadingMore() }


        mGroupCampaignsAdapter.setOnViewItemClick { campaignId ->
            activity?.startActivity<CampaignActivity>(
                    CampaignActivity.EXTRA_KEY_GROUP_ID to groupId,
                    CampaignActivity.EXTRA_KEY_CAMPAIGN_ID to campaignId
            )
        }

        retryImg.setOnClickListener { mPresenter.onRetryClick() }

        arguments?.let {
            groupId = it.getString(BUNDLE_KEY_GROUP_ID)
            mPresenter.onViewPrepared(groupId)
        }


    }

    override fun showEmptyResult() {
        emptyLayout.visible
    }

    override fun setFieldError(value: Boolean) {
        mGroupCampaignsAdapter.isFieldError = value
    }

    override fun showProgress() {
        progressBarLayout.visible
    }

    override fun hideProgress() {
        progressBarLayout.gone
    }

    override fun setLoadingMoreDone() {
        mGroupCampaignsAdapter.setLoadMoreDone()
    }


    override fun updateCampaign(campaigns: MutableList<GroupCampaigns>) {
        mGroupCampaignsAdapter.addCampaign(campaigns)
    }


    override fun showRetryImg() {
        retryImg.visible
    }

    override fun hideRetryImg() {
        retryImg.invisible
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDetach()
    }

}