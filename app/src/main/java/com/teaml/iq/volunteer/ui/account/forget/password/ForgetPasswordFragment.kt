package com.teaml.iq.volunteer.ui.account.forget.password

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.account.forget.password.emailsend.EmailSendSuccessfullyFragment
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.utils.replaceFragment
import kotlinx.android.synthetic.main.forget_password_layout.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 05/02/2018.
 *
 */
class ForgetPasswordFragment : BaseFragment(), ForgetPasswordMvpView {


    companion object {
        val TAG: String = ForgetPasswordFragment::class.java.simpleName

        fun newInstance(args: Bundle? =  null): ForgetPasswordFragment =
                ForgetPasswordFragment().apply { arguments = args }
    }

    @Inject
    lateinit var mPresenter: ForgetPasswordMvpPresenter<ForgetPasswordMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = layoutInflater.inflate(R.layout.forget_password_layout, container, false)

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnResetPassword.setOnClickListener {
            val email = emailField.text.toString()
            mPresenter.onResetPasswordClick(email)
        }

        btnBack.setOnClickListener { mPresenter.onBackClick() }
    }

    override fun setup(view: View) {

    }

    override fun showEmailSendSuccessfullyFragment() {
        activity?.replaceFragment(R.id.rootView, EmailSendSuccessfullyFragment.newInstance(), EmailSendSuccessfullyFragment.TAG)
    }
    override fun showLoginFragment() { activity?.supportFragmentManager?.popBackStack() }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }


}