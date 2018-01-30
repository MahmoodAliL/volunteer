package com.teaml.iq.volunteer.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.teaml.iq.volunteer.MvpApp
import com.teaml.iq.volunteer.di.component.ActivityComponent
import com.teaml.iq.volunteer.di.component.DaggerActivityComponent
import com.teaml.iq.volunteer.di.module.ActivityModule

/**
 * Created by Mahmood Ali on 30/01/2018.
 *
 */
class BaseActivity : AppCompatActivity(), MvpView {


    private var mAppCompatActivity: ActivityComponent? = null

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        mAppCompatActivity = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent((application as MvpApp).applicationComponent)
                .build()

    }


    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(msg: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onError(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(msg: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(msg: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isNetworkConnection(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideKeyboard() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun openSignInActivity() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}