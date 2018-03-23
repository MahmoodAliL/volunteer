package com.teaml.iq.volunteer.ui.campaign.members

import android.os.Bundle
import android.view.View
import com.teaml.iq.volunteer.data.model.CampaignMembers
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataFragment
import com.teaml.iq.volunteer.ui.campaign.detail.CampaignDetailFragment
import com.teaml.iq.volunteer.ui.campaign.members.adapter.CampaignMembersAdapter
import kotlinx.android.synthetic.main.recycler_view_layout.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 21/02/2018.
 */
open class CampaignMembersFragment : BaseLoadDataFragment<CampaignMembers>(), CampaignMembersMvpView {

    @Inject
    lateinit var mPresenter: CampaignMembersMvpPresenter<CampaignMembersMvpView>

    @Inject
    lateinit var mAdapter: CampaignMembersAdapter


    companion object {
        val TAG: String = CampaignMembersFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = CampaignMembersFragment().apply { arguments = args }
    }

    override fun onCreateRecyclerAdapter(): BaseRecyclerAdapter<CampaignMembers> {
        return mAdapter
    }

    override fun initActivityComponent() {
        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }
    }

    override fun setup(view: View) {

        arguments?.let {

            val campaignId = it.getString(CampaignDetailFragment.BUNDLE_KEY_CAMPAIGN_ID)

            retryImg.setOnClickListener { mPresenter.onRetryClick() }
            mAdapter.setOnLoadMoreListener { mPresenter.onLoadMore() }
            swipeRefreshLayout.setOnRefreshListener { mPresenter.onSwipeRefresh() }
            mPresenter.onViewPrepared(campaignId)

        }


    }

    override fun addMembers(members: List<CampaignMembers>) {
        mAdapter.addItems(members)
    }


    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}