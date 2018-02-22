package com.teaml.iq.volunteer.ui.main.group


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.GroupInfo
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.utils.gone
import com.teaml.iq.volunteer.utils.invisible
import com.teaml.iq.volunteer.utils.visible
import kotlinx.android.synthetic.main.progressbar_layout.*
import kotlinx.android.synthetic.main.recycler_view_layout.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class GroupFragment : BaseFragment(), GroupMvpView {


    companion object {
        val TAG = GroupFragment::class.java.simpleName

        fun newInstance(args: Bundle? = null): GroupFragment = GroupFragment().apply { arguments = args }
    }

    @Inject
    lateinit var mGroupAdapter: GroupAdapter

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mPresenter: GroupMvpPresenter<GroupMvpView>

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

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = mLinearLayoutManager
        recyclerView.adapter = mGroupAdapter
        mGroupAdapter.initRecyclerView(recyclerView)

        mGroupAdapter.setOnLoadingMoreListener { mPresenter.onLoadingMore() }
        retryImg.setOnClickListener { mPresenter.onRetryClick() }

        mPresenter.onViewPrepared()

    }


    override fun showEmptyResult() {
        emptyLayout.visible
    }

    override fun setFieldError(value: Boolean) {
        mGroupAdapter.isFieldError = value
    }

    override fun showProgress() {
        progressBarLayout.visible
    }

    override fun hideProgress() {
        progressBarLayout.gone
    }

    override fun setLoadingMoreDone() {
        mGroupAdapter.setLoadMoreDone()
    }

    override fun updateGroups(listOfgroupInfo: MutableList<GroupInfo>) {
        mGroupAdapter.addGroups(listOfgroupInfo)
    }

    override fun showRetryImg() {
        retryImg.visible
    }

    override fun hideRetryImg() {
        retryImg.invisible
    }


    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }






}// Required empty public constructor
