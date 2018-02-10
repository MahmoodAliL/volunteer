package com.teaml.iq.volunteer.ui.main

import android.os.Bundle
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseActivity
import com.teaml.iq.volunteer.ui.main.group.GroupFragment
import com.teaml.iq.volunteer.ui.main.home.HomeFragment
import com.teaml.iq.volunteer.ui.main.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class MainActivity : BaseActivity() , MainMvpView {


    @Inject
    lateinit var mPresenter: MainMvpPresenter<MainMvpView>

    @Inject
    lateinit var mBottomBarAdapter: BottomBarAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent.inject(this)
        mPresenter.onAttach(this)

        setup()


        // when the item select first time
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            mPresenter.onNavigationItemSelected(item.itemId)
            true
        }

    }

    override fun setup() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        with(mBottomBarAdapter) {
            addFragment(HomeFragment.newInstance())
            addFragment(GroupFragment.newInstance())
            addFragment(ProfileFragment.newInstance())
        }

        viewPager.adapter = mBottomBarAdapter
    }

    override fun showHomeFragment() {
        viewPager.setCurrentItem(0, false)
    }

    override fun showGroupFragment() {
        viewPager.setCurrentItem(1, false)
    }

    override fun viewProfileFragment() {
        viewPager.setCurrentItem(2, false)
    }



}
