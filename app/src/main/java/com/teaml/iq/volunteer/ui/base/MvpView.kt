package com.teaml.iq.volunteer.ui.base

import android.support.annotation.StringRes

/**
 * Created by Mahmood Ali on 20/01/2018.
 *
 * Base interface that any class that wants to act as a View in the MVP (Model View Presenter)
 * pattern must implement. Generally this interface will be extended by a more specific interface
 * that then usually will be implemented by an Activity or Fragment.
 */
interface MvpView {

    fun showLoading()

    fun hideLoading()

    fun onError(@StringRes msg: Int)

    fun onError(msg: String)

    fun showMessage(@StringRes msg: Int)

    fun showMessage(msg: String)

    fun isNetworkConnection(): Boolean

    fun hideKeyboard()

    // openActivityOnTakenExpire()
    fun openSignInActivity()
}