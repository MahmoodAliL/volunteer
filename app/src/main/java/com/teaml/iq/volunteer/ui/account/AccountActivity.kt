package com.teaml.iq.volunteer.ui.account

import android.os.Bundle
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.account.basicinfo.BasicInfoFragment
import com.teaml.iq.volunteer.ui.account.signin.SignInFragment
import com.teaml.iq.volunteer.ui.base.BaseActivity
import com.teaml.iq.volunteer.utils.addFragment
import com.teaml.iq.volunteer.utils.replaceFragment
import javax.inject.Inject

class AccountActivity : BaseActivity(), AccountMvpView {


    companion object {
        const val EXTRA_CURRENT_FRAGMENT = "current_fragment"
    }

    enum class CurrentFragment(val type: Int) {

        BASE_INFO_FRAGMENT(0),
        SING_IN_FRAGMENT(1);
    }


    @Inject lateinit var mPresenter: AccountMvpPresenter<AccountMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        val currentFragmentId = intent.extras?.getInt(EXTRA_CURRENT_FRAGMENT)
                ?: CurrentFragment.SING_IN_FRAGMENT.type

        activityComponent.inject(this)
        mPresenter.onAttach(this)

        mPresenter.decideCurrentFragment(currentFragmentId)

    }


    override fun showSignInFragment() {
        addFragment(R.id.rootView, SignInFragment.newInstance(), SignInFragment.TAG)
    }

    override fun showBaseProfileInfoFragment() {
        replaceFragment(R.id.rootView, BasicInfoFragment.newInstance(), BasicInfoFragment.TAG)
    }

}
