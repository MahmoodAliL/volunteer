package com.teaml.iq.volunteer.ui.campaign.members.rate


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.teaml.iq.volunteer.R

import com.teaml.iq.volunteer.data.model.RateMembers
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataFragment
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import com.teaml.iq.volunteer.ui.campaign.detail.CampaignDetailFragment
import com.teaml.iq.volunteer.ui.campaign.members.adapter.RateMemberAdapter
import com.teaml.iq.volunteer.utils.gone
import kotlinx.android.synthetic.main.rate_member_view.view.*
import kotlinx.android.synthetic.main.recycler_view_layout.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */

class RateMemberFragment : BaseLoadDataFragment<RateMembers>(), RateMemberMvpView {

    companion object {
        val TAG = RateMemberFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = RateMemberFragment().apply { arguments = args }

        const val BUNDLE_KEY_CAMPAIGN_ID = "bundle_key_campaign_id"

    }

    @Inject
    lateinit var mRatePresenter: RateMemberMvpPresenter<RateMemberMvpView>

    @Inject
    lateinit var mRateAdapter: RateMemberAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        with(activity as CampaignActivity) {
            supportActionBar?.title = getString(R.string.rate_member)
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
        }
        super.onCreate(savedInstanceState)

    }


    override fun onCreateRecyclerAdapter(): BaseRecyclerAdapter<RateMembers> {
        return mRateAdapter
    }

    override fun initActivityComponent() {
        getActivityComponent()?.let {
            it.inject(this)
            mRatePresenter.onAttach(this)
        }
    }

    override fun setup(view: View) {
        arguments?.let {
            val campaignId = it.getString(BUNDLE_KEY_CAMPAIGN_ID)
            retryImg.setOnClickListener { mRatePresenter.onRetryClick() }
            mRateAdapter.setOnLoadMoreListener { mRatePresenter.onLoadMore() }
            swipeRefreshLayout.setOnRefreshListener { mRatePresenter.onSwipeRefresh() }

            mRatePresenter.onViewPrepared(campaignId)


            mRateAdapter.onItemClickListener {
                showMessage("click")
            }
            mRateAdapter.onHelpfulClickListener { userId , position->
                mRatePresenter.onHelpfulClick(campaignId,userId,position)
            }
            mRateAdapter.onUnhelpfulClickListener { userId, position ->
                mRatePresenter.onUnhelpfulClick(campaignId,userId,position)
            }
            mRateAdapter.onNotAttendClickListener { userId, position ->
                mRatePresenter.onNotAttendClick(campaignId,userId,position)
            }
        }
    }

    override fun enableClickable(position: Int) {
        val item = recyclerView.layoutManager.findViewByPosition(position)
        val screen = item.findViewById<View>(R.id.whiteScreen)
        screen.gone
    }

    override fun removeMemberView(position: Int) {
        mRateAdapter.onItemRemove(position)
    }

    override fun addMembers(members: List<RateMembers>) {
        mRateAdapter.addItems(members)
    }

    override fun onDestroyView() {
        mRatePresenter.onDetach()
        super.onDestroyView()
    }


}// Required empty public constructor
