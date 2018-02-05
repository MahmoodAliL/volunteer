package com.teaml.iq.volunteer.ui.account.forget.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment

/**
 * Created by Mahmood Ali on 05/02/2018.
 *
 */
class ForgetPasswordFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = layoutInflater.inflate(R.layout.forget_password_layout, container, false)

        return view
    }


    override fun setup(view: View) {

    }


}