package com.teaml.iq.volunteer.ui.main.myactivity

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.ui.account.AccountActivity
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.main.home.CampaignAdapter
import com.teaml.iq.volunteer.utils.gone
import com.teaml.iq.volunteer.utils.invisible
import com.teaml.iq.volunteer.utils.visible
import kotlinx.android.synthetic.main.myactivity_not_sign_in.*
import kotlinx.android.synthetic.main.progressbar_layout.*
import kotlinx.android.synthetic.main.recycler_view_layout.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
class MyActivityFragment : BaseFragment(), MyActivityMvpView{


    @Inject
    lateinit var mPresenter: MyActivityMvpPresenter<MyActivityMvpView>

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mCampaignAdapter: CampaignAdapter

    companion object {
        val TAG: String = MyActivityFragment::class.java.simpleName
        fun newInstance( args: Bundle = Bundle.EMPTY) = MyActivityFragment().apply { arguments = args }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var layout = R.layout.myactivity_not_sign_in

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
            layout = mPresenter.decideCurrentLayout()
        }

        return layoutInflater.inflate(layout, container, false)
    }

    override fun setup(view: View) {
        mPresenter.onViewPrepared()
    }

    override fun setupViewWithSignInStatus() {
        mLinearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.adapter = mCampaignAdapter
        recyclerView.setHasFixedSize(true)
        mCampaignAdapter.initRecyclerView(recyclerView)

        mCampaignAdapter.setOnLoadingMoreListener { mPresenter.onLoadingMore() }

        retryImg.setOnClickListener { mPresenter.onRetryClick() }

    }

    override fun setupViewWithSignOutStatus() {
        btnSignIn.setOnClickListener { mPresenter.onSignInClick() }
    }


    override fun openSignInActivity() {
        activity?.startActivity<AccountActivity>()
    }


    override fun showRetryImg() {
        retryImg.visible
    }

    override fun hideRetryImg() {
        retryImg.gone
    }

    override fun setFieldError(value: Boolean) {
        mCampaignAdapter.isFieldError = value
    }

    override fun setLoadingMoreDone() {
        mCampaignAdapter.isLoading = false
    }

    override fun showProgress() {
        progressBarLayout.visible
    }

    override fun hideProgress() {
        progressBarLayout.invisible
    }

    override fun showEmptyResult() {
        emptyLayout.visible
    }

    override fun updateCampaign(campaignPosts: MutableList<CampaignPost>) {
        mCampaignAdapter.addCampaigns(campaignPosts)
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}