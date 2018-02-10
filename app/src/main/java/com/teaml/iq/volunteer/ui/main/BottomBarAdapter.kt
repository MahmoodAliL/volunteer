package com.teaml.iq.volunteer.ui.main

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

/**
 * Created by Mahmood Ali on 08/02/2018.
 */
class BottomBarAdapter(fragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(fragmentManager) {

    private val fragments = mutableListOf<Fragment>()

    /**
     * Return the Fragment associated with a specified position.
     */
    override fun getItem(position: Int): Fragment = fragments[position]

    /**
     * Return the number of views available.
     */
    override fun getCount(): Int = fragments.count()


    fun addFragment(fragment: Fragment) = fragments.add(fragment)

}