package com.teaml.iq.volunteer.ui.account.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.account.basicinfo.BasicInfoFragment
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.utils.replaceFragment
import kotlinx.android.synthetic.main.sign_up_layout.*
import javax.inject.Inject


class SignUpFragment : BaseFragment(), SignUpMvpView {

    @Inject
    lateinit var mPresenter: SignUpMvpPresenter<SignUpMvpView>

    companion object {
        val TAG: String = SignUpFragment::class.java.simpleName
        fun newInstance(args: Bundle? = null): SignUpFragment = SignUpFragment().apply { arguments = args }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.sign_up_layout, container, false)
        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignUp.setOnClickListener {

            val email = emailField.text.toString()
            val password = passwordField.text.toString()
            val confirmPassword = confirmPasswordField.text.toString()

            mPresenter.onSignUpClick(email, password, confirmPassword )
        }

        btnSignIn.setOnClickListener {
            mPresenter.onSignInClick()
        }
    }

    override fun showSignInFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }


    override fun showBasicInfoFragment() {
        activity?.apply {
            // remove sign up from stack then sign in will show
            // so if user click back button will exist from app
            supportFragmentManager.popBackStack()
            // replace sign in with basic user info
            replaceFragment(
                    R.id.rootView,
                    BasicInfoFragment.newInstance(),
                    BasicInfoFragment.TAG
            )
        }

    }

    override fun setup(view: View) {

    }


    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}// Required empty public constructor
