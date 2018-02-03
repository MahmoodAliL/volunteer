package com.teaml.iq.volunteer.ui.account.signin


import android.net.wifi.aware.AttachCallback
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_signin.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class SigninFragment : BaseFragment() , SigninMvpView {

    @Inject
    lateinit var mPresenter: SigninMvpPresenter<SigninMvpView>


    companion object {

        // get the name of fragment
        val TAG = SigninFragment::class.java.simpleName

        // get new instance from this fragment
        fun newInstance(): SigninFragment = SigninFragment().apply {
            val args = Bundle()
            arguments = args
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view:View = inflater.inflate(R.layout.fragment_signin, container, false)
        val component = getActivityComponent()

        component?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        btnDone.setOnClickListener {
            mPresenter.onSignInClick(emailField.editText?.text.toString(),passwordField.editText?.text.toString())
        }
        btnSignUp.setOnClickListener {
            //TODO go to sign up fragment
        }


    }

    override fun setup(view: View) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mPresenter.onDetach()
    }

}// Required empty public constructor
