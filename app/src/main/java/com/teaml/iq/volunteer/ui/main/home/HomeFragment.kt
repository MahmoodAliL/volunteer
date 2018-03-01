package com.teaml.iq.volunteer.ui.main.home

import android.os.Bundle
import android.view.View
import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataFragment
import kotlinx.android.synthetic.main.recycler_view_layout.*
import javax.inject.Inject


class HomeFragment : BaseLoadDataFragment<CampaignPost>(), HomeMvpView {


    @Inject
    lateinit var mPresenter: HomeMvpPresenter<HomeMvpView>

    @Inject
    lateinit var mCampaignAdapter: CampaignAdapter


    companion object {
        val TAG: String = HomeFragment::class.java.simpleName

        fun newInstance(): HomeFragment = HomeFragment().apply {
            arguments = Bundle()
        }
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

        retryImg.setOnClickListener { mPresenter.onRetryClick() }
        swipeRefreshLayout.setOnRefreshListener { mPresenter.onSwipeRefresh() }

        mPresenter.onViewPrepared()

    }


    override fun addNewItems(newItems: MutableList<CampaignPost>) {
        mCampaignAdapter.addItems(newItems)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDetach()
    }


}// Required empty public constructor
