package com.teaml.iq.volunteer.ui.campaign.add


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.DatePicker
import android.widget.TimePicker
import com.google.android.gms.common.GooglePlayServicesNotAvailableException
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.GeoPoint
import com.satsuware.usefulviews.LabelledSpinner
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.data.model.SelectedDate
import com.teaml.iq.volunteer.data.model.SelectedTime
import com.teaml.iq.volunteer.ui.base.BaseFragment
import com.teaml.iq.volunteer.ui.campaign.map.CustomMapView
import com.teaml.iq.volunteer.ui.group.detail.GroupDetailFragment
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.replaceFragment
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.fragment_add_campaign.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.bundleOf
import org.jetbrains.anko.find
import java.util.*
import javax.inject.Inject


/**
 * A simple [Fragment] subclass.
 */
open class AddCampaignFragment : BaseFragment(), AddCampaignMvpView, LabelledSpinner.OnItemChosenListener,
        TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, OnMapReadyCallback {

    companion object {
        val TAG: String = AddCampaignFragment::class.java.simpleName

        fun newInstance(args: Bundle = Bundle.EMPTY) = AddCampaignFragment().apply { arguments = args }
    }


    private lateinit var mDatePickerDialog: DatePickerDialog
    private lateinit var mTimePickerDialog: TimePickerDialog

    protected var mSelectedDate: SelectedDate? = null
    protected var mSelectedTime: SelectedTime? = null

    private var mEditMenuItem: MenuItem? = null

    protected var mVolunteersGender = DataManager.UserGender.ANY
    protected var mLocation = GeoPoint(32.6049946, 44.0098118)

    protected lateinit var mMapView: CustomMapView

    @Inject
    lateinit var mPresenter: AddCampaignMvpPresenter<AddCampaignMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        with(activity as AppCompatActivity) {
            supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_close_24dp)
            supportActionBar?.title = getString(R.string.add_campaign)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add_campaign, container, false)
        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)

        }

        mMapView = view.find(R.id.googleMap)
        mMapView.onCreate(savedInstanceState)

        return view
    }


    override fun setup(view: View) {

        setUpDatePicker()
        setUpTimePicker()

        spinnerGender.onItemChosenListener = this

        imgCampaign.setOnClickListener { mPresenter.onImgClick() }

        startTimeField.setOnClickListener { mTimePickerDialog.show() }

        startDateField.setOnClickListener { mDatePickerDialog.show() }


        getSyncGoogleMap()
    }

    open protected fun getSyncGoogleMap() {
        mMapView.getMapAsync(this)
    }

    override fun showMyGroupFragment(groupId: String) {

        activity?.supportFragmentManager?.popBackStack()
        activity?.replaceFragment(
                R.id.fragmentContainer,
                GroupDetailFragment.newInstance(bundleOf(GroupDetailFragment.BUNDLE_KEY_GROUP_ID to groupId)),
                GroupDetailFragment.TAG
        )
    }

    override fun openCropImg() {
        context?.let {
            CropImage.activity()
                    .setAspectRatio(16, 9)
                    .setRequestedSize(720, 576)
                    .setOutputCompressFormat(Bitmap.CompressFormat.JPEG)
                    .setOutputCompressQuality(60)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .start(it, this)
        }
    }

    override fun updateImg(uri: Uri) {
        GlideApp.with(this)
                .load(uri)
                .placeholder(R.drawable.campaign_placeholder_img)
                .into(imgCampaign)
    }

    private fun setUpTimePicker() {
        val calender = Calendar.getInstance()
        val hourOfDay = calender.get(Calendar.HOUR_OF_DAY)
        val min = calender.get(Calendar.MINUTE)

        mTimePickerDialog = TimePickerDialog(activity, this, hourOfDay, min, false)
        mTimePickerDialog.setTitle("add the begin time")
    }

    private fun setUpDatePicker() {
        val calender = Calendar.getInstance()

        val currentDay = calender.get(Calendar.DAY_OF_YEAR)

        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        // campaign will be set on the next day
        calender.set(Calendar.DAY_OF_YEAR, currentDay + 1)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        // set min date
        mDatePickerDialog = DatePickerDialog(activity, this, year, month, day)
        mDatePickerDialog.datePicker.minDate = calender.timeInMillis
    }


    override fun onTimeSet(timePicker: TimePicker?, hourOfDay: Int, minute: Int) {


        mSelectedTime = SelectedTime(hourOfDay, minute)

        val time = "$hourOfDay:$minute"
        startTimeField.setText(time)
    }

    override fun onDateSet(datePicker: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

        mSelectedDate = SelectedDate(year, month + 1, dayOfMonth)

        val date = "$year/$month/$dayOfMonth"
        startDateField.setText(date)
    }

    override fun showReadExternalStorageRationale() {
        activity?.alert(R.string.read_external_storage_rationale_for_select_image, null, {
            positiveButton(R.string.ok) {
                mPresenter.onRequestReadExternalStoragePermission()
            }
            negativeButton(R.string.cancel) { }
        })?.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        mPresenter.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.edit_profile_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        // hide editMenu
        mEditMenuItem = menu?.findItem(R.id.action_edit)
        mEditMenuItem?.isVisible = false
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_done -> {
                val name = titleField.text.toString()
                val description = descriptionField.text.toString()
                val gender = mVolunteersGender
                val age = ageField.text.toString()
                val maxMember = maxMemberField.text.toString()

                mPresenter.onActionDoneClick(name, mSelectedTime, mSelectedDate, mLocation, description, gender, age.toIntOrNull(), maxMember.toIntOrNull())

            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onItemChosen(labelledSpinner: View?, adapterView: AdapterView<*>?, itemView: View?, position: Int, id: Long) {
        when (labelledSpinner?.id) {
            R.id.spinnerGender -> {
                mVolunteersGender = when (position) {
                    0 -> DataManager.UserGender.ANY
                    1 -> DataManager.UserGender.MALE
                    else -> DataManager.UserGender.FEMALE
                }

            }
        }
    }

    override fun onNothingChosen(labelledSpinner: View?, adapterView: AdapterView<*>?) {

    }

    override fun onMapReady(googleMap: GoogleMap) {


        try {
            MapsInitializer.initialize(this.activity)
        } catch (e: GooglePlayServicesNotAvailableException) {
            Log.e(TAG, e.message)
        }

        val marker = MarkerOptions().alpha(0.8f)

        val karbala = LatLng(mLocation.latitude, mLocation.longitude)

        googleMap.addMarker(MarkerOptions().position(karbala))

        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(karbala, AppConstants.MAP_ZOOM))

        googleMap.setOnMapClickListener {
            googleMap.clear()
            googleMap.animateCamera(CameraUpdateFactory.newLatLng(it))
            googleMap.addMarker(marker.position(it))
            mLocation = GeoPoint(it.latitude, it.longitude)
        }
    }

    override fun onResume() {
        super.onResume()
        mMapView.onResume()

    }


    override fun onLowMemory() {
        super.onLowMemory()
        mMapView.onLowMemory()
    }
    override fun onDestroyView() {
        mEditMenuItem?.isVisible = true

        mPresenter.onDetach()
        super.onDestroyView()
        mMapView.onDestroy()
    }

}// Required empty public constructor
