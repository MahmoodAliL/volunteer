package com.teaml.iq.volunteer.ui.account.signin


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.account.basicinfo.BasicInfoFragment
import com.teaml.iq.volunteer.ui.account.forget.password.ForgetPasswordFragment
import com.teaml.iq.volunteer.ui.account.signup.SignUpFragment
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.main.MainActivity
import com.teaml.iq.volunteer.utils.replaceFragment
import com.teaml.iq.volunteer.utils.replaceFragmentAndAddToBackStack
import kotlinx.android.synthetic.main.sign_in_fragment.*
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
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
        val view = inflater.inflate(R.layout.sign_in_fragment, container, false)

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        //something changed
        return view
    }


    override fun setup(view: View) {

        btnSignIn.setOnClickListener {

            val email = emailField.text.toString()
            val password = passwordField.text.toString()

            mPresenter.onSignInClick(email, password)
        }

        btnSignUp.setOnClickListener { mPresenter.onSignUpClick() }

        btnForgetPassword.setOnClickListener { mPresenter.onForgetPasswordClick() }

        backImgView.setOnClickListener { mPresenter.onBackImgClick() }

    }

    override fun showPreviousActivityOrExit() {
        activity?.onBackPressed()
    }


    override fun showSignUpFragment() {
        activity?.replaceFragmentAndAddToBackStack(
                R.id.rootView,
                SignUpFragment.newInstance(),
                SignUpFragment.TAG
        )
    }

    override fun showBasicInfoFragment() {
        activity?.replaceFragment(
                R.id.rootView,
                BasicInfoFragment.newInstance(),
                BasicInfoFragment.TAG
        )
    }

    override fun showForgetPasswordFragment() {
        //Extension function
        activity?.replaceFragmentAndAddToBackStack(
                R.id.rootView,
                ForgetPasswordFragment.newInstance(),
                ForgetPasswordFragment.TAG
        )
    }


    override fun openMainActivity() {
        activity?.apply {
            startActivity(intentFor<MainActivity>().clearTask().newTask())
        }

    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }


}// Required empty public constructor