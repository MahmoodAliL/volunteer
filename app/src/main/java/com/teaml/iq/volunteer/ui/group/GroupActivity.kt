package com.teaml.iq.volunteer.ui.group

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseActivity
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity.Companion.EXTRA_KEY_GROUP_ID
import com.teaml.iq.volunteer.ui.campaign.detail.CampaignDetailFragment.Companion.BUNDLE_KEY_GROUP_ID
import com.teaml.iq.volunteer.ui.group.detail.GroupDetailFragment
import com.teaml.iq.volunteer.utils.addFragment
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
class GroupActivity : BaseActivity(), GroupMvpView {


    companion object {
        val TAG: String = GroupActivity::class.java.simpleName
    }

    @Inject
    lateinit var mLinearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var mPresenter: GroupMvpPresenter<GroupMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.fragment_container_with_toolbar)
        activityComponent?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            mPresenter.OnBackStackChangedListener(supportFragmentManager.backStackEntryCount)
        }
        setup()
    }

    override fun updateToolbarToGroupCampaigns() {
        supportActionBar?.title = getString(R.string.group_campaigns)
    }

    override fun updateToolbarToGroupDetail() {
        supportActionBar?.title = getString(R.string.group_detail)
    }

    override fun setup() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.group_detail)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun showGroupDetailFragment() {

        intent?.let {
            val groupId = it.getStringExtra(EXTRA_KEY_GROUP_ID)
            val bundle = Bundle()
            bundle.putString(BUNDLE_KEY_GROUP_ID, groupId)
            addFragment(R.id.fragmentContainer, GroupDetailFragment.newInstance(bundle), GroupDetailFragment.TAG)

        }
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}