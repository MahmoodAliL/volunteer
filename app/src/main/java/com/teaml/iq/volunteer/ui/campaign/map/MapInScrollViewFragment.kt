package com.teaml.iq.volunteer.ui.campaign.map

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.google.android.gms.maps.SupportMapFragment

/**
 * Created by Mahmood Ali on 10/03/2018.
 */
class MapInScrollViewFragment : SupportMapFragment() {

    companion object {
        val TAG: String = MapInScrollViewFragment::class.java.simpleName
    }

    private var mOnTouch: (() -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, viewGroup: ViewGroup?, savedInstance: Bundle?): View? {
        val mapView = super.onCreateView(inflater, viewGroup, savedInstance)

        activity?.let {
            val frameLayout = TouchableWrapper(it)
            frameLayout.setBackgroundColor(Color.TRANSPARENT)
            (mapView as ViewGroup).addView(
                    frameLayout,
                    ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            )
        }

        return mapView
    }


    fun setOnTouchListener(onTouch: (() -> Unit)?) {
        Log.d(TAG, "listener set")
        mOnTouch = onTouch
    }

    inner class TouchableWrapper(ctx: Context) : FrameLayout(ctx) {
        override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
            when(ev?.action) {
                MotionEvent.ACTION_DOWN -> { mOnTouch?.invoke();  Log.d(TAG, "actionDown")}
                MotionEvent.ACTION_UP -> mOnTouch?.invoke()
            }

            return super.dispatchTouchEvent(ev)
        }
    }

}