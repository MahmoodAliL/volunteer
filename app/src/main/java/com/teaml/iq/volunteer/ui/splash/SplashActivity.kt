package com.teaml.iq.volunteer.ui.splash

import android.content.Intent
import android.os.Bundle
import com.teaml.iq.volunteer.ui.account.AccountActivity
import com.teaml.iq.volunteer.ui.base.BaseActivity
import com.teaml.iq.volunteer.ui.intro.MainIntroActivity
import com.teaml.iq.volunteer.ui.main.MainActivity
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 01/02/2018 at 00:49.
 */
class SplashActivity : BaseActivity(), SplashMvpView {



    @Inject lateinit var mPresenter: SplashMvpPresenter<SplashMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent.inject(this)
        mPresenter.onAttach(this)

    }

    override fun setup() {

    }

    override fun openIntroActivityForResult(requestCode: Int) {
        val intent = Intent(this, MainIntroActivity::class.java)
        startActivityForResult(intent, requestCode)
    }

    override fun openMainActivity() {
        startActivity<MainActivity>()
        finish()
    }

    override fun openBaseInfoActivity() {
        val data = AccountActivity.EXTRA_CURRENT_FRAGMENT to AccountActivity.CurrentFragment.BASE_INFO_FRAGMENT.type
       startActivity<AccountActivity>(data)
        finish()
    }

    override fun finishActivity() = finish()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }


    override fun onDestroy() {
        mPresenter.onDetach()
        super.onDestroy()
    }

}