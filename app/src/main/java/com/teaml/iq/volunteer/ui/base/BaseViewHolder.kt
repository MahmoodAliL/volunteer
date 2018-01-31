package com.teaml.iq.volunteer.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Mahmood Ali on 31/01/2018.
 */
abstract class BaseViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    var currentPosition: Int = 0
        private set

    protected abstract fun clear()

    fun onBind(position: Int) {
        currentPosition = position
        clear()
    }
}