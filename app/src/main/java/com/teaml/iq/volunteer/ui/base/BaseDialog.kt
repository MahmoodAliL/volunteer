package com.teaml.iq.volunteer.ui.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.RelativeLayout
import com.teaml.iq.volunteer.di.component.ActivityComponent

/**
 * Created by Mahmood Ali on 31/01/2018.
 */
abstract class BaseDialog : DialogFragment(), DialogMvpView {

    var baseActivity: BaseActivity? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is BaseActivity) {
            baseActivity = context
            baseActivity?.onFragmentAttached()
        }

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // the default content of dialog
        val root = RelativeLayout(context)
        root.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        // dialog
        val dialog = Dialog(context)
        dialog.setContentView(root)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            // TODO: Know why add these again to windows (remove it when test dialog)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        }

        // prevent user from cancel dialog
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)

        return dialog
    }


    override fun show(manager: FragmentManager, tag: String) {
        val transaction  = manager.beginTransaction()
        val prefFragment =  manager.findFragmentByTag(tag)
        if (prefFragment != null) {
            transaction.remove(prefFragment)
        }
        transaction.addToBackStack(null)
        show(transaction, tag)
    }

    override fun dismissDialog(tag: String) {
        dismiss()
        baseActivity?.onFragmentAttached()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup(view)
    }

    protected abstract fun setup(view: View)

    fun getActivityComponent(): ActivityComponent? = baseActivity?.activityComponent

    override fun showLoading(msg: String) {
        baseActivity?.showLoading(msg)
    }

    override fun showLoading(@StringRes msg: Int) {
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

}