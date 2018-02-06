package com.teaml.iq.volunteer.ui.main.profile


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class ProfileFragment : BaseFragment() {

    companion object {
        val TAG = ProfileFragment::class.java.simpleName

        fun newInstance(): ProfileFragment = ProfileFragment().apply {
            val args = Bundle()
            arguments = args
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun setup(view: View) {

    }

}// Required empty public constructor
