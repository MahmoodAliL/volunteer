package com.teaml.iq.volunteer.ui.main.myactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.ui.account.AccountActivity
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataFragment
import com.teaml.iq.volunteer.ui.main.home.CampaignAdapter
import kotlinx.android.synthetic.main.myactivity_not_sign_in.*
import kotlinx.android.synthetic.main.recycler_view_layout.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
class MyActivityFragment : BaseLoadDataFragment<CampaignPost>(), MyActivityMvpView{

    @Inject
    lateinit var mPresenter: MyActivityMvpPresenter<MyActivityMvpView>

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

    override fun onCreateRecyclerAdapter(): BaseRecyclerAdapter<CampaignPost> {
        return mCampaignAdapter
    }

    override fun initActivityComponent() {
        // لن نكون بحاجة اليها لاننا قمنا بتهائتها في onCreateView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // just to override
        setup(view)
    }

    override fun setup(view: View) {
        mPresenter.onViewPrepared()
    }


    override fun setupViewWithSignInStatus() {

        setupLoadDateView()

        mCampaignAdapter.setOnLoadMoreListener { mPresenter.onLoadMore() }
        swipeRefreshLayout.setOnRefreshListener { mPresenter.onSwipeRefresh() }
        retryImg.setOnClickListener { mPresenter.onRetryClick() }

    }

    override fun setupViewWithSignOutStatus() {
        btnSignIn.setOnClickListener { mPresenter.onSignInClick() }
    }


    override fun openSignInActivity() {
        activity?.startActivity<AccountActivity>()
    }


    override fun updateCampaign(campaignPosts: MutableList<CampaignPost>) {
        mCampaignAdapter.addItems(campaignPosts)
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}