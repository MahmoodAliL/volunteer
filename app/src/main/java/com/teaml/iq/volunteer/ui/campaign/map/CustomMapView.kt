package com.teaml.iq.volunteer.ui.campaign.map

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import com.google.android.gms.maps.GoogleMapOptions
import com.google.android.gms.maps.MapView

/**
 * Created by ali on 3/10/2018.
 */
class CustomMapView : MapView {

    constructor(context: Context, options: GoogleMapOptions) : super(context, options)

    @JvmOverloads
    constructor(context: Context, attr: AttributeSet? = null, defStyle: Int = 0 ) : super(context, attr, defStyle)

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        when (ev?.action) {
            MotionEvent.ACTION_UP -> {
                this.parent.requestDisallowInterceptTouchEvent(false)
            }
            MotionEvent.ACTION_DOWN -> {
                this.parent.requestDisallowInterceptTouchEvent(true)
            }
        }
        return super.dispatchTouchEvent(ev)
    }
}