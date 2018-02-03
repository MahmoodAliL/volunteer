package com.teaml.iq.volunteer.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import com.heinrichreimersoftware.materialintro.app.IntroActivity
import com.teaml.iq.volunteer.ui.base.BaseActivity
import com.teaml.iq.volunteer.ui.intro.MainIntroActivity
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 01/02/2018 at 00:49.
 */
class SplashActivity : BaseActivity(), SplashMvpView {



    @Inject lateinit var presenter: SplashMvpPresenter<SplashMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityComponent.inject(this)
        presenter.onAttach(this)

    }

    override fun openIntroActivityForResult(requestCode: Int) {
        val intent = Intent(this, MainIntroActivity::class.java)
        startActivityForResult(intent, requestCode)
    }

    override fun openMainActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openBaseInfoActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun finishActivity() = finish()


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        presenter.onActivityResult(requestCode, resultCode, data)
    }


}