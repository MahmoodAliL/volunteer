package com.teaml.iq.volunteer.ui.account.basicinfo


import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.DatePicker
import com.satsuware.usefulviews.LabelledSpinner
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BaseFragment
import kotlinx.android.synthetic.main.base_user_info_layout.*
import java.util.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */

class BasicInfoFragment
    : BaseFragment(), BasicInfoMvpView , LabelledSpinner.OnItemChosenListener, DatePickerDialog.OnDateSetListener {

    companion object {
        val TAG: String = BasicInfoFragment::class.java.simpleName
        fun newInstance(args: Bundle? = null)  = BasicInfoFragment().apply { arguments = args  }
    }

    @Inject
    lateinit var mPresenter: BasicInfoMvpPresenter<BasicInfoMvpView>

    @Inject
    lateinit var datePickerDialog: DatePickerDialog


    // user info
    private lateinit var gender: DataManager.UserGender
    private var birthOfDate: Long = 0L


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.base_user_info_layout, container, false)

        activityComponent?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        birthOfDayField.setOnClickListener {
            datePickerDialog.show()
        }

        btnDone.setOnClickListener {

            val name = nameField.text.toString()
            mPresenter.onDoneClick(name, gender, birthOfDate)
        }

    }

    override fun onDateSet(dataPicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        birthOfDate = GregorianCalendar(year, month, dayOfMonth).timeInMillis
    }

    override fun setup(view: View) {
        spinnerGender.onItemChosenListener = this
    }

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }


    /**
     * Callback method to be invoked when an item in this LabelledSpinner's
     * spinner view has been selected. This callback is invoked only when
     * the newly selected position is different from the previously selected
     * position or if there was no selected item.
     *
     * @param labelledSpinner The LabelledSpinner where the selection
     * happened. This view contains the AdapterView.
     * @param adapterView The AdapterView where the selection happened. Note
     * that this AdapterView is part of the LabelledSpinner
     * component.
     * @param itemView The view within the AdapterView that was clicked.
     * @param position The position of the view in the adapter.
     * @param id The row id of the item that is selected.
     */
    override fun onItemChosen(labelledSpinner: View?, adapterView: AdapterView<*>, itemView: View?, position: Int, id: Long) {

        when(labelledSpinner?.id) {
            R.id.spinnerGender -> {
                gender = if (position == 0) DataManager.UserGender.MALE  else DataManager.UserGender.FEMALE
            }
        // If we have multiple LabelledSpinners, you can add more cases here
        }

    }

    /**
     * Callback method to be invoked when the selection disappears from this
     * view. The selection can disappear for instance when touch is activated
     * or when the adapter becomes empty.
     *
     * @param labelledSpinner The LabelledSpinner view that contains the
     * AdapterView.
     * @param adapterView The AdapterView that now contains no selected item.
     */
    override fun onNothingChosen(labelledSpinner: View?, adapterView: AdapterView<*>?) {
    }

}// Required empty public constructor
