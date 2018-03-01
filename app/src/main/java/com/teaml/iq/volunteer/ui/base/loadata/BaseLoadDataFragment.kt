package com.teaml.iq.volunteer.ui.base.loadata

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.utils.gone
import com.teaml.iq.volunteer.utils.visible
import kotlinx.android.synthetic.main.progressbar_layout.*
import kotlinx.android.synthetic.main.recycler_view_layout.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 27/02/2018.
 */
abstract class BaseLoadDataFragment<TypeOfList> : BaseFragment(), BaseLoadDataMvpView {


    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    private var mAdapter: BaseRecyclerAdapter<TypeOfList>? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        initActivityComponent()

        return inflater.inflate(R.layout.recycler_view_layout, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupLoadDateView()
        // default status is disable
        swipeRefreshLayout.isEnabled = false
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setup(view: View) {

    }


    abstract fun initActivityComponent()

    abstract fun onCreateRecyclerAdapter(): BaseRecyclerAdapter<TypeOfList>

    protected fun setupLoadDateView() {

        mAdapter = onCreateRecyclerAdapter()
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setDistanceToTriggerSync(250)

        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.adapter = mAdapter
        recyclerView.setHasFixedSize(true)
        mAdapter?.initRecyclerView(recyclerView)

    }

    override fun hideRefreshProgress() {
        swipeRefreshLayout.isRefreshing = false
    }

    override fun enableSwipeRefreshLayout(isEnable: Boolean) {
        swipeRefreshLayout.isEnabled = isEnable
    }

    override fun isProgressShow() =  progressBarLayout.isShown

    override fun clearList() {
        mAdapter?.clearList()
    }

    override fun showRetryImg() {
        retryImg.visible
    }

    override fun hideRetryImg() {
        retryImg.gone
    }

    override fun enableLoadMore(isEnable: Boolean) {
        mAdapter?.enableLoadMore(isEnable)
    }

    override fun setLoadMoreComplete() {
        mAdapter?.setLoadMoreComplete()
    }

    override fun showProgress() {
        swipeRefreshLayout.isEnabled = false
        progressBarLayout.visible
    }

    override fun hideProgress() {
        swipeRefreshLayout.isEnabled = true
        progressBarLayout.gone
    }

    override fun showEmptyResult() {
        emptyLayout.visible
    }

    override fun hideEmptyResult() {
        emptyLayout.gone
    }


}