package com.teaml.iq.volunteer.ui.main.myactivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.account.AccountActivity
import com.teaml.iq.volunteer.ui.base.BaseFragment
import kotlinx.android.synthetic.main.myactivity_not_sign_in.*
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
class MyActivityFragment : BaseFragment(), MyActivityMvpView{


    @Inject
    lateinit var mPresenter: MyActivityMvpPresenter<MyActivityMvpView>


    companion object {
        val TAG: String = MyActivityFragment::class.java.simpleName
        fun newInstance( args: Bundle = Bundle.EMPTY) = MyActivityFragment().apply { arguments = args }

        const val BUNDLE_KEY_LAYOUT_TYPE = "bundle_key_layout_type"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        var layout = R.layout.myactivity_not_sign_in

        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
            layout = mPresenter.decideCurrentLayout()
        }

        return layoutInflater.inflate(layout, container, false)
    }

    override fun setup(view: View) {
        mPresenter.onViewPrepared()
    }

    override fun setupViewWithSignInStatus() {

    }

    override fun setupViewWithSignOutStatus() {
        btnSignIn.setOnClickListener { mPresenter.onSignInClick() }
    }


    override fun openSignInActivity() {
        activity?.startActivity<AccountActivity>()
    }


    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}