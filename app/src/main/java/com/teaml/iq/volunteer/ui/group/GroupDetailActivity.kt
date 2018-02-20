package com.teaml.iq.volunteer.ui.group

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.BaseActivity
import kotlinx.android.synthetic.main.group_detail_layout.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
class GroupDetailActivity : BaseActivity(), GroupDetailMvpView{


    companion object {
        val TAG: String = GroupDetailActivity::class.java.simpleName
    }

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mPresenter: GroupDetailMvpPresenter<GroupDetailMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.group_detail_layout)
        activityComponent?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        setup()
    }

    override fun setup() {

        val list = (1..9).map { GroupCampaigns("","title of campaign $it","campaign.jpg", "2018/04/0$it" ) }.toMutableList()
        recyclerView.layoutManager = mLinearLayoutManager
        val adapter = GroupCampaignsAdapter(list)
        recyclerView.setHasFixedSize(true)
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = adapter
        Log.e(TAG,recyclerView.adapter.itemCount.toString() )


    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}