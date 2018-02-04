package com.teaml.iq.volunteer.ui.main.group


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.account.signin.SigninFragment
import com.teaml.iq.volunteer.ui.base.BaseFragment
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class GroupFragment : BaseFragment() , GroupMvpView {


    companion object {
        val TAG = GroupFragment::class.java.simpleName

        fun newInstance(): GroupFragment = GroupFragment().apply {
            val args = Bundle()
            arguments = args
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_group, container, false)
    }

    override fun setup(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //TODO detach the fragment
    }


}// Required empty public constructor
