package com.teaml.iq.volunteer.ui.main

import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

/**
 * Created by Mahmood Ali on 08/02/2018.
 */
class NoSwipViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    private var mEnabled = false

    override fun onTouchEvent(ev: MotionEvent?): Boolean {
        if (mEnabled)
            return super.onTouchEvent(ev)
        return false
    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        if (mEnabled)
            return super.onInterceptTouchEvent(ev)
        return false
    }

    fun setPagingEnable(enabled: Boolean) {
        mEnabled = enabled
    }

}