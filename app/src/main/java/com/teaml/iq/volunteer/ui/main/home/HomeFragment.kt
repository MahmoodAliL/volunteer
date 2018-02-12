package com.teaml.iq.volunteer.ui.main.home

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.utils.gone
import com.teaml.iq.volunteer.utils.invisible
import com.teaml.iq.volunteer.utils.visible
import kotlinx.android.synthetic.main.recycler_view_layout.*
import javax.inject.Inject


class HomeFragment : BaseFragment(), HomeMvpView {

    @Inject
    lateinit var mPresenter: HomeMvpPresenter<HomeMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mCampaignAdapter: CampaignAdapter


    companion object {
        val TAG: String = HomeFragment::class.java.simpleName

        fun newInstance(): HomeFragment = HomeFragment().apply {
            arguments = Bundle()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.recycler_view_layout, container, false)

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }




        return view
    }

    override fun setup(view: View) {

        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.adapter = mCampaignAdapter
        recyclerView.setHasFixedSize(true)
        mCampaignAdapter.initRecyclerView(recyclerView)

        mCampaignAdapter.setOnLoadingMoreListener { mPresenter.onLoadingMore() }
        retryImg.setOnClickListener { mPresenter.onRetryClick() }

        mPresenter.onViewPrepared()


        /* btnCenterRetry.setOnClickListener { mPresenter.onRetryClick() }
         btnBottomRetry.setOnClickListener {  }


        activity?.let { activity ->
            db.collection("campaign").get().addOnSuccessListener(activity) {
                val campaignList: MutableList<FbCampaign> = it.toObjects(FbCampaign::class.java)

                campaignList.do
            }
        }*/

    }


    override fun showEmptyResult() {
        emptyLayout.visible
    }

    override fun setFieldError(value: Boolean) {
        mCampaignAdapter.isFieldError = value
    }

    override fun showProgress() {
        progressBarLayout.visible
    }

    override fun hideProgress() {
        progressBarLayout.gone
    }

    override fun setLoadingMoreDone() {
        mCampaignAdapter.setLoadMoreDone()
    }


    override fun updateCampaign(campaignPosts: MutableList<CampaignPost>) {
        mCampaignAdapter.addCampaigns(campaignPosts)
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


}// Required empty public constructor
