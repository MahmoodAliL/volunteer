package com.teaml.iq.volunteer.ui.account.forget.password.emailsend

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment
import kotlinx.android.synthetic.main.email_send_successfully_layout.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 06/02/2018.
 */
class EmailSendSuccessfullyFragment : BaseFragment(), EmailSendSuccessfullyMvpView {

    companion object {
        val TAG: String = EmailSendSuccessfullyFragment::class.java.simpleName

        fun newInstance(args: Bundle? = null): EmailSendSuccessfullyFragment = EmailSendSuccessfullyFragment().apply { arguments = args }
    }

    @Inject
    lateinit var mPresenter: EmailSendMvpPresenter<EmailSendSuccessfullyMvpView>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = layoutInflater.inflate(R.layout.email_send_successfully_layout, container, false)

        if (activityComponent != null) {
            activityComponent?.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }

    override fun setup(view: View) {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnBack.setOnClickListener { mPresenter.onBackClick() }
    }

    override fun showLoginFragment() {
        activity?.supportFragmentManager?.popBackStack()
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }
}