package com.teaml.iq.volunteer.ui.campaign

import android.os.Bundle
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseActivity
import com.teaml.iq.volunteer.ui.campaign.detail.CampaignDetailFragment
import com.teaml.iq.volunteer.utils.addFragment
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 15/02/2018.
 */
class CampaignActivity : BaseActivity(), CampaignMvpView {


    companion object {
        val TAG: String = CampaignActivity::class.java.simpleName

        const val EXTRA_KEY_GROUP_ID = "extra_key_group_id"
        const val EXTRA_KEY_CAMPAIGN_ID = "extra_key_campaign_id"
    }

    override fun setup() {

    }

    @Inject
    lateinit var mPresenter: CampaignMvpPresenter<CampaignMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container_with_toolbar)

        activityComponent?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        setSupportActionBar(toolbar)


        supportFragmentManager.addOnBackStackChangedListener {
            mPresenter.addOnBackStackChangedListener(supportFragmentManager.backStackEntryCount)
        }

    }

    override fun updateToolbarToDetail() {
        supportActionBar?.title = getString(R.string.campaign_detail)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
    }

    override fun updateToolbarToMember() {
        supportActionBar?.title = getString(R.string.member)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24dp)
    }

    override fun showDetailFragment() {

        intent?.let {
            val groupId = it.getStringExtra(EXTRA_KEY_GROUP_ID)
            val campaignId = it.getStringExtra(EXTRA_KEY_CAMPAIGN_ID)

            val bundle = Bundle()
            bundle.putString(CampaignDetailFragment.BUNDLE_KEY_CAMPAIGN_ID, campaignId)
            bundle.putString(CampaignDetailFragment.BUNDLE_KEY_GROUP_ID, groupId)
            addFragment(R.id.fragmentContainer, CampaignDetailFragment.newInstance(bundle), CampaignDetailFragment.TAG)
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }


}