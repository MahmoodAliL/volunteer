package com.teaml.iq.volunteer.ui.profile

import android.os.Bundle
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseActivity
import com.teaml.iq.volunteer.ui.profile.info.ProfileInfoFragment
import com.teaml.iq.volunteer.utils.addFragment
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 13/02/2018.
 */

class ProfileActivity : BaseActivity(), ProfileMvpView {


    @Inject
    lateinit var mPresenter: ProfileMvpPresenter<ProfileMvpView>

    companion object {
        const val EXTRA_KEY_UID = "extra_key_uid"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_container_with_toolbar)

        val uid= intent?.getStringExtra(EXTRA_KEY_UID)

        activityComponent?.let {
            it.inject(this)
            mPresenter.onAttach(this)
            mPresenter.loadUserInfo(uid)
        }

        supportFragmentManager.addOnBackStackChangedListener {
            mPresenter.onBackStackChangedListener(supportFragmentManager.backStackEntryCount)
        }

        setup()
    }

    override fun updateToolbarToEditProfile() {
        supportActionBar?.title = getString(R.string.edit_profile)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24dp)
    }

    override fun updateToolbarToProfileInfo() {
        supportActionBar?.title = getString(R.string.profile_info)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_arrow_back_24dp)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun setup() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.profile_info)
    }

    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

    override fun showProfileInfoFragment(uid: String) {
        val bundle = Bundle()
        bundle.putString(ProfileInfoFragment.BUNDLE_KEY_UID, uid)
        addFragment(R.id.fragmentContainer, ProfileInfoFragment.newInstance(bundle), ProfileInfoFragment.TAG)
    }


}