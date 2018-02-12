package com.teaml.iq.volunteer.ui.main.myactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
class MyActivityFragment : BaseFragment(), MyActivityMvpView{


    companion object {
        val TAG: String = MyActivityFragment::class.java.simpleName
        fun newInstance( args: Bundle = Bundle.EMPTY) = MyActivityFragment().apply { arguments = args }

        const val BUNDLE_KEY_LAYOUT_TYPE = "bundle_key_layout_type"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (arguments == null)
            super.onCreateView(inflater, container, savedInstanceState)


        val layout = arguments!!.getInt(BUNDLE_KEY_LAYOUT_TYPE,  R.layout.myactivity_not_sign_in)
        val view = layoutInflater.inflate(layout, container, false)


        return view
    }

    override fun setup(view: View) {

    }

}