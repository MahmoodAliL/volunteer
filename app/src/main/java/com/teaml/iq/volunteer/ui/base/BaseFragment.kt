package com.teaml.iq.volunteer.ui.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View
import dmax.dialog.SpotsDialog

/**
 * Created by Mahmood Ali on 31/01/2018.
 */
class BaseFragment : Fragment(), MvpView {

    private var baseActivity: BaseActivity? = null
    // TODO :: check if we can use activity dialog
    private var progressDialog: SpotsDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is BaseActivity) {
            baseActivity = context
            baseActivity?.onFragmentAttached()
        }

    }


    override fun showLoading(msg: String) {
        baseActivity?.showLoading(msg)
    }

    override fun hideLoading() {
        baseActivity?.hideLoading()
    }

    override fun onError(@StringRes msg: Int) {
        baseActivity?.onError(msg)
    }

    override fun onError(msg: String) {
        baseActivity?.onError(msg)
    }

    override fun showMessage(@StringRes msg: Int) {
        baseActivity?.showMessage(msg)
    }

    override fun showMessage(msg: String) {
        baseActivity?.showMessage(msg)
    }

    override fun isNetworkConnection(): Boolean {
        return baseActivity?.isNetworkConnection() == true
    }

    override fun hideKeyboard() {
        baseActivity?.hideKeyboard()
    }

    override fun openSignInActivity() {
        baseActivity?.openSignInActivity()
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    protected fun setup(view: View) {}

    interface callback {

        fun onFragmentAttached()

        fun onFragmentDetach(tag: String)
    }

}