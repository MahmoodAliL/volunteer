package com.teaml.iq.volunteer.ui.account.profileInfo


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teaml.iq.volunteer.R


/**
 * A simple [Fragment] subclass.
 */
class BaseProfileInfoFragment : Fragment() {


    companion object {
        val TAG: String = BaseProfileInfoFragment::class.java.simpleName
        fun newInstance(args: Bundle? = null)  = BaseProfileInfoFragment().apply { arguments = args  }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base_profile_info, container, false)
    }



}// Required empty public constructor
