package com.teaml.iq.volunteer.ui.account.signin


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.account.signup.SignUpFragment
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.utils.replaceFragmentAndAddToBackStack
import kotlinx.android.synthetic.main.fragment_signin.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class SignInFragment : BaseFragment(), SignInMvpView {

    companion object {
        val TAG: String = SignInFragment::class.java.simpleName
        fun newInstance(args: Bundle? = null): SignInFragment = SignInFragment().apply { arguments = args }
    }

    @Inject
    lateinit var mPresenter: SignInMvpPresenter<SignInMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signin, container, false)

        if (activityComponent != null) {
            activityComponent?.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSignIn.setOnClickListener {

            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            mPresenter.onBtnSignInClicked(email, password)
        }


        btnSignUp.setOnClickListener {
            mPresenter.onBtnSignUpClicked()
        }
    }

    override fun setup(view: View) {

    }

    override fun showSignUpFragment() {
        activity?.replaceFragmentAndAddToBackStack(
                R.id.rootView,
                SignUpFragment.newInstance(),
                SignUpFragment.TAG
        )
    }


}// Required empty public constructor
