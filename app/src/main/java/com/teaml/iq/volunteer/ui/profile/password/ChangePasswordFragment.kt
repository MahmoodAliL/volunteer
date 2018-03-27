package com.teaml.iq.volunteer.ui.profile.password


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.*

import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_change_password.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
class ChangePasswordFragment : BaseFragment(), ChangePasswordMvpView {

    companion object {
        val TAG = ChangePasswordFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = ChangePasswordFragment().apply { arguments  = args }
    }

    @Inject
    lateinit var mPresenter: ChangePasswordMvpPresenter<ChangePasswordMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        with(activity as AppCompatActivity) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24dp)
            supportActionBar?.title = getString(R.string.change_password)


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }
        return inflater.inflate(R.layout.fragment_change_password, container, false)
    }

    override fun setup(view: View) {

    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.edit_profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_done -> {
                val oldPassword = oldPasswordField.text.toString()
                val newPassword = passwordField.text.toString()
                val confirmPassword = confirmPasswordField.text.toString()

                mPresenter.onDoneClick(oldPassword,newPassword,confirmPassword)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showProfileInfoFragment() {
        activity?.onBackPressed()
    }



    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}// Required empty public constructor
