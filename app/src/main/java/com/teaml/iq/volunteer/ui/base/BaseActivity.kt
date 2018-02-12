package com.teaml.iq.volunteer.ui.base

import android.annotation.TargetApi
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.teaml.iq.volunteer.MvpApp
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.di.component.ActivityComponent
import com.teaml.iq.volunteer.di.component.DaggerActivityComponent
import com.teaml.iq.volunteer.di.module.ActivityModule
import com.teaml.iq.volunteer.ui.account.AccountActivity
import com.teaml.iq.volunteer.utils.KeyboardUtils
import com.teaml.iq.volunteer.utils.NetworkUtils
import dmax.dialog.SpotsDialog
import org.jetbrains.anko.clearTask
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.newTask
import org.jetbrains.anko.toast

/**
 * Created by Mahmood Ali on 30/01/2018.
 *
 */
abstract class BaseActivity : AppCompatActivity(), MvpView, BaseFragment.Callback {


    private var progressDialog: SpotsDialog? = null

    var activityComponent: ActivityComponent? = null
        private set

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent = DaggerActivityComponent.builder()
                .activityModule(ActivityModule(this))
                .applicationComponent((application as MvpApp).applicationComponent)
                .build()
    }

    abstract fun setup()

    @TargetApi(Build.VERSION_CODES.M)
    fun requestPermissionsSafely(permission: Array<out String>, requestCode: Int) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            requestPermissions(permission, requestCode)
    }

    @TargetApi(Build.VERSION_CODES.M)
    fun hasPermission(permission: String): Boolean {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M ||
                checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
    }

    override fun onFragmentAttached() {

    }

    override fun onFragmentDetach(tag: String) {

    }

    override fun showLoading(msg: String ) {
        if (progressDialog == null) {
            progressDialog = SpotsDialog(this, msg, R.style.SpotsDialogCustom)
            progressDialog?.setCancelable(false)
        }

        progressDialog?.show()
    }

    override fun showLoading(@StringRes msg: Int) {
        val message = getString(msg) ?: getString(R.string.loading)
        showLoading(message)
    }


    override fun hideLoading() {
        // if dialog is not showing then hide it
        // TODO : check  if  progressDialog?.isShowing == false  is work fine with out check null
        if ( progressDialog != null && progressDialog?.isShowing == true ) {
            progressDialog?.dismiss()
        }
    }

    private fun showSnakbar(msg: String) {
        val snakbar = Snackbar.make(findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_LONG)
        val txtView = snakbar.view.findViewById<TextView>(R.id.snackbar_text)
        txtView.setTextColor(Color.WHITE)
        snakbar.show()
    }

    override fun onError(@StringRes msg: Int) {
        val message = getString(msg)

        if (message != null)
            showSnakbar(message)
        else
            showSnakbar(getString(R.string.some_error))
    }


    override fun onError(msg: String) {
        showSnakbar(msg)
    }

    override fun showMessage(@StringRes msg: Int) {
        if (getString(msg) != null)
            toast(msg).show()
        else
            toast(R.string.some_error).show()
    }

    override fun showMessage(msg: String) {
        toast(msg).show()
    }

    override fun isNetworkConnection(): Boolean = NetworkUtils.isNetworkConnected(applicationContext)

    override fun hideKeyboard() {
        KeyboardUtils.hideSoftInput(this)
    }

    override fun openSignInActivity() {
        startActivity(intentFor<AccountActivity>().clearTask().newTask())
    }
}