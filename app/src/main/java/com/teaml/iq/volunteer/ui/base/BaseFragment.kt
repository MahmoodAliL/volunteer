package com.teaml.iq.volunteer.ui.base

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View

/**
 * Created by Mahmood Ali on 31/01/2018.
 */
abstract class BaseFragment : Fragment(), FragmentMvpView {

    var mBaseActivity: BaseActivity? = null
        private set


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            mBaseActivity = context
            mBaseActivity?.onFragmentAttached()
        }

    }


    fun getActivityComponent() = mBaseActivity?.activityComponent

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
    }
    override fun getBaseActivity(): BaseActivity? = mBaseActivity


    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permission: Array<out String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permission, requestCode)
    }

    override fun getBaseFragment(): BaseFragment  = this

    override fun showLoading(msg: String) {
        mBaseActivity?.showLoading(msg)
    }

    override fun showLoading(msg: Int) {
        mBaseActivity?.showLoading(msg)
    }

    override fun hideLoading() {
        mBaseActivity?.hideLoading()
    }

    override fun onError(@StringRes msg: Int) {
        mBaseActivity?.onError(msg)
    }

    override fun onError(msg: String) {
        mBaseActivity?.onError(msg)
    }

    override fun showMessage(@StringRes msg: Int) {
        mBaseActivity?.showMessage(msg)
    }

    override fun showMessage(msg: String) {
        mBaseActivity?.showMessage(msg)
    }

    override fun isNetworkConnection(): Boolean {
        return mBaseActivity?.isNetworkConnection() == true
    }

    override fun hideKeyboard() {
        mBaseActivity?.hideKeyboard()
    }

    override fun openSplashActivity() {
        mBaseActivity?.openSplashActivity()
    }

    override fun onDetach() {
        mBaseActivity = null
        super.onDetach()
    }


    override fun openSignInActivityWhenTokenExpire() {
        mBaseActivity?.openSignInActivityWhenTokenExpire()
    }

    protected abstract fun setup(view: View)


    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetach(tag: String)
    }

}