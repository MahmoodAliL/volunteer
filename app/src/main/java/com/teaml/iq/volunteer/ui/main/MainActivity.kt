package com.teaml.iq.volunteer.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
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


    companion object {
        fun getStartIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent.inject(this)
        mPresenter.onAttach(this)

        setSupportActionBar(toolbar)


        bottomNavigation.selectedItemId = R.id.home

        // when the item select first time
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            mPresenter.onNavigationItemSelected(item.itemId)
            return@setOnNavigationItemSelectedListener true
        }


    }
    override fun viewHomeFragment() {
        openFragment(HomeFragment.newInstance())
    }

    override fun viewGroupFragment() {
        openFragment(GroupFragment.newInstance())
    }

    override fun viewProfileFragment() {
        openFragment(ProfileFragment.newInstance())
    }

    private fun openFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.mainContent,fragment)
                .commit()
    }


}
