package com.teaml.iq.volunteer.ui.account

import android.os.Bundle
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseActivity
import javax.inject.Inject

class AccountActivity : BaseActivity(), AccountMvpView {



    @Inject lateinit var mPresenter: AccountMvpPersenter<AccountMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account)

        activityComponent.inject(this)
        mPresenter.onAttach(this)

    }




}
