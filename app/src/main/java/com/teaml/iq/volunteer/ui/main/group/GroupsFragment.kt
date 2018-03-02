package com.teaml.iq.volunteer.ui.main.group


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.View
import com.teaml.iq.volunteer.data.model.GroupInfo
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataFragment
import kotlinx.android.synthetic.main.recycler_view_layout.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class GroupsFragment : BaseLoadDataFragment<GroupInfo>(), GroupsMvpView {


    companion object {
        val TAG = GroupsFragment::class.java.simpleName
        fun newInstance(args: Bundle? = null): GroupsFragment = GroupsFragment().apply { arguments = args }
    }

    @Inject
    lateinit var mGroupAdapter: GroupAdapter

    @Inject
    lateinit var mPresenter: GroupsMvpPresenter<GroupsMvpView>

    override fun initActivityComponent() {
        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }
    }

    override fun onCreateRecyclerAdapter(): BaseRecyclerAdapter<GroupInfo> {
        return mGroupAdapter
    }

    override fun setup(view: View) {


        mGroupAdapter.initRecyclerView(recyclerView)

        mGroupAdapter.setOnLoadMoreListener { mPresenter.onLoadMore() }
        retryImg.setOnClickListener { mPresenter.onRetryClick() }
        swipeRefreshLayout.setOnRefreshListener { mPresenter.onSwipeRefresh() }
        mPresenter.onViewPrepared()

    }


    override fun updateGroups(listOfGroupInfo: MutableList<GroupInfo>) {
        mGroupAdapter.addItems(listOfGroupInfo)
    }


    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }


}// Required empty public constructor
