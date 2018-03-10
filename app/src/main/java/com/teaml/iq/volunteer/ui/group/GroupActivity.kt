package com.teaml.iq.volunteer.ui.group

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseActivity
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity.Companion.EXTRA_KEY_GROUP_ID
import com.teaml.iq.volunteer.ui.campaign.detail.CampaignDetailFragment.Companion.BUNDLE_KEY_GROUP_ID
import com.teaml.iq.volunteer.ui.group.create.CreateGroupFragment
import com.teaml.iq.volunteer.ui.group.detail.GroupDetailFragment
import com.teaml.iq.volunteer.utils.addFragment
import com.teaml.iq.volunteer.utils.replaceFragment
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
class GroupActivity : BaseActivity(), GroupMvpView {

    companion object {
        val TAG: String = GroupActivity::class.java.simpleName
        const val EXTRA_CURRENT_FRAGMENT = "EXTRA_CURRENT_FRAGMENT"
        /*const val EXTRA_KEY_GROUP_ID = ""*/

    }


    enum class FragmentType(val type: Int) {
        CREATE_GROUP(0),
        GROUP_DETAIL(1)
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


            intent?.let {
                val fragmentType = it.getIntExtra(EXTRA_CURRENT_FRAGMENT, FragmentType.GROUP_DETAIL.type)
                mPresenter.decideCurrentFragment(fragmentType)
            }


        }

        supportFragmentManager.addOnBackStackChangedListener {
            mPresenter.onBackStackChangedListener(supportFragmentManager.backStackEntryCount)
        }
        setup()
    }


    override fun showCreateGroupFragment() {
        addFragment(
                R.id.fragmentContainer,
                CreateGroupFragment.newInstance(),
                CreateGroupFragment.TAG
        )
    }

    override fun updateToolbarToGroupCampaigns() {
        supportActionBar?.title = getString(R.string.group_campaigns)
    }

    override fun updateToolbarToGroupDetail() {
        supportActionBar?.title = getString(R.string.group_detail)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
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
            replaceFragment(
                    R.id.fragmentContainer,
                    GroupDetailFragment.newInstance(bundle),
                    GroupDetailFragment.TAG
            )

        }
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}