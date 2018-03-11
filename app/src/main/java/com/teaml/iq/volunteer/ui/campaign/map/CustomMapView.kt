package com.teaml.iq.volunteer.ui.campaign.map

import android.content.Context
import android.view.MotionEvent
import com.google.android.gms.maps.MapView

/**
 * Created by ali on 3/10/2018.
 */
class CustomMapView(context: Context): MapView(context) {

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when(ev?.action) {
            MotionEvent.ACTION_UP -> {
                this.parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_DOWN-> {
                this.parent.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}