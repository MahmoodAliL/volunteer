package com.teaml.iq.volunteer.ui.campaign.members

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.CampaignMembers
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.campaign.detail.CampaignDetailFragment
import com.teaml.iq.volunteer.ui.campaign.members.adapter.CampaignMembersAdapter
import com.teaml.iq.volunteer.utils.gone
import com.teaml.iq.volunteer.utils.visible
import kotlinx.android.synthetic.main.progressbar_layout.*
import kotlinx.android.synthetic.main.recycler_view_layout.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 21/02/2018.
 */
class CampaignMembersFragment : BaseFragment(), CampaignMembersMvpView {


    @Inject
    lateinit var mPresenter: CampaignMembersMvpPresenter<CampaignMembersMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mAdapter: CampaignMembersAdapter


    companion object {
        val TAG: String = CampaignMembersFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = CampaignMembersFragment().apply { arguments = args }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        (activity as AppCompatActivity).supportActionBar?.title = "Members"

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return inflater.inflate(R.layout.recycler_view_layout, container, false)
    }

    override fun setup(view: View) {

        arguments?.let {

            val campaignId = it.getString(CampaignDetailFragment.BUNDLE_KEY_CAMPAIGN_ID)

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = mLinearLayoutManager
            recyclerView.adapter = mAdapter
            mAdapter.initRecyclerView(recyclerView)

            retryImg.setOnClickListener { mPresenter.onRetryClick() }
            mAdapter.setOnLoadingMoreListener { mPresenter.onLoadingMore() }

            mPresenter.onViewPrepared(campaignId)

        }


    }

    override fun addMembers(members: List<CampaignMembers>) {
        mAdapter.addMembers(members)
    }

    override fun hideProgress() {
        progressBarLayout.gone
    }

    override fun showProgress() {
        progressBarLayout.visible
    }

    override fun showRetryImg() {
        retryImg.visible
    }

    override fun hideRetryImg() {
        retryImg.gone
    }

    override fun showEmptyResult() {
        emptyLayout.visible
    }

    override fun hideEmptyResult() {
        emptyLayout.gone
    }

    override fun setFieldError(value: Boolean) {
        mAdapter.isFieldError = value
    }

    override fun setLoadingMoreDone() {
       mAdapter.setLoadMoreDone()
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}