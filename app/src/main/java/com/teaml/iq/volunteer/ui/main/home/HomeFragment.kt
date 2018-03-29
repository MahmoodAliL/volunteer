package com.teaml.iq.volunteer.ui.main.home

import android.os.Bundle
import android.view.View
import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataFragment
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import kotlinx.android.synthetic.main.recycler_view_layout.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject


class HomeFragment : BaseLoadDataFragment<CampaignPost>(), HomeMvpView {


    @Inject
    lateinit var mPresenter: HomeMvpPresenter<HomeMvpView>

    @Inject
    lateinit var mCampaignAdapter: CampaignAdapter


    companion object {
        val TAG: String = HomeFragment::class.java.simpleName

        fun newInstance(args: Bundle = Bundle.EMPTY) = HomeFragment().apply { arguments = args }
    }

    override fun initActivityComponent() {
        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }
    }


    override fun onCreateRecyclerAdapter(): BaseRecyclerAdapter<CampaignPost> {
        return mCampaignAdapter
    }

    override fun setup(view: View) {

        mCampaignAdapter.setOnLoadMoreListener { mPresenter.onLoadMore() }
        mCampaignAdapter.setOnViewItemClickListener { campaignId, groupId ->
            mPresenter.onViewItemClick(campaignId, groupId)
        }

        retryImg.setOnClickListener { mPresenter.onRetryClick() }
        swipeRefreshLayout.setOnRefreshListener { mPresenter.onSwipeRefresh() }


        mPresenter.onViewPrepared()

    }

    override fun openCampaignActivityWithDetailFragment(campaignId: String, groupId: String) {
        activity?.startActivity<CampaignActivity>(
                CampaignActivity.EXTRA_KEY_CAMPAIGN_ID to campaignId,
                CampaignActivity.EXTRA_KEY_GROUP_ID to groupId
        )
    }

    override fun addNewItems(newItems: MutableList<CampaignPost>) {
        mCampaignAdapter.addItems(newItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDetach()
    }


}// Required empty public constructor
