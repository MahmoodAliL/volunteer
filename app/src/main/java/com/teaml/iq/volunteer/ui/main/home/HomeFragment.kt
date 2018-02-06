package com.teaml.iq.volunteer.ui.main.home

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment
import javax.inject.Inject


class HomeFragment : BaseFragment() , HomeMvpView {

    @Inject
    lateinit var mPresenter: HomeMvpPresenter<HomeMvpView>


    companion object {
        val TAG = HomeFragment::class.java.simpleName

        fun newInstance(): HomeFragment = HomeFragment().apply {
            arguments = Bundle()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        if (activityComponent != null) {
            activityComponent?.inject(this)
        }
        return view
    }

    override fun setup(view: View) {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDetach()
    }


}// Required empty public constructor
