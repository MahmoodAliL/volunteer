package com.teaml.iq.volunteer.ui.main

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseActivity
import com.teaml.iq.volunteer.ui.main.group.GroupsFragment
import com.teaml.iq.volunteer.ui.main.home.HomeFragment
import com.teaml.iq.volunteer.ui.main.myaccount.MyAccountFragment
import com.teaml.iq.volunteer.ui.main.myactivity.MyActivityFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : BaseActivity(), MainMvpView {


    companion object {
        val TAG: String = MainActivity::class.java.simpleName
    }

    @Inject
    lateinit var mPresenter: MainMvpPresenter<MainMvpView>

    @Inject
    lateinit var mBottomBarAdapter: BottomBarAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityComponent?.inject(this)
        mPresenter.onAttach(this)


        setup()


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.action_search -> {
            toast("on clicked"); true
        }

        else -> super.onOptionsItemSelected(item)
    }


    override fun setup() {

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        val homeItem = AHBottomNavigationItem(getString(R.string.home), R.drawable.ic_home_black_24dp)
        val groupItem = AHBottomNavigationItem(getString(R.string.group), R.drawable.ic_group_dark_grey_24dp)
        val myActivityItem = AHBottomNavigationItem(getString(R.string.activity), R.drawable.ic_notifications_black_24dp)
        val myAccountItem = AHBottomNavigationItem(getString(R.string.account), R.drawable.ic_person_black_24dp)

        bottomNavigationBar.addItem(homeItem)
        bottomNavigationBar.addItem(groupItem)
        bottomNavigationBar.addItem(myActivityItem)
        bottomNavigationBar.addItem(myAccountItem)

        bottomNavigationBar.accentColor = Color.parseColor("#673AB7")
        bottomNavigationBar.inactiveColor = Color.parseColor("#757575")

        bottomNavigationBar.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

        bottomNavigationBar.isBehaviorTranslationEnabled = false
        //bottomNavigationBar.isColored = true

        bottomNavigationBar.setOnTabSelectedListener { position, wasSelected ->

            if (!wasSelected) {
                viewPager.setCurrentItem(position, false)
            }
            true
        }

        // لكي تمنع عادة حدوث اعادة تحميل البيانات الخاصة بالفراكمنت بمعنى اخر تمنع اعادة انشاء الفراكمنت
        // و تسمح بالبقاء البيانات في الرام
        viewPager.offscreenPageLimit = 4

        mBottomBarAdapter.addFragment(HomeFragment.newInstance())
        mBottomBarAdapter.addFragment(GroupsFragment.newInstance())
        mBottomBarAdapter.addFragment(MyActivityFragment.newInstance())
        mBottomBarAdapter.addFragment(MyAccountFragment.newInstance())
        viewPager.adapter = mBottomBarAdapter

        //mPresenter.onAddFragmentToBottomBar()


    }


}
