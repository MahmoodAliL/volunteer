package com.teaml.iq.volunteer.ui.main.myaccount


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment


/**
 * A simple [Fragment] subclass.
 */
class MyAccountFragment : BaseFragment() {

    companion object {
        val TAG: String = MyAccountFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY ) = MyAccountFragment().apply { arguments = args }

        const val BUNDLE_KEY_LAYOUT_TYPE = "bundle_key_layout_type"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        if (arguments == null)
            return super.onCreateView(inflater, container, savedInstanceState)

        val layout = arguments!!.getInt(BUNDLE_KEY_LAYOUT_TYPE, R.layout.myaccount_layout_not_sign_in)

        Log.d(TAG, layout.toString())
        val view = inflater.inflate(layout, container, false)


        return view
    }

    override fun setup(view: View) {

    }

}// Required empty public constructor
