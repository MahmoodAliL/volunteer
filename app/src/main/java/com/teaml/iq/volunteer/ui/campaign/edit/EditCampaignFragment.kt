package com.teaml.iq.volunteer.ui.campaign.edit

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.data.model.SelectedDate
import com.teaml.iq.volunteer.data.model.SelectedTime
import com.teaml.iq.volunteer.ui.campaign.add.AddCampaignFragment
import com.teaml.iq.volunteer.ui.campaign.detail.CampaignDetailFragment
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.toDateString
import com.teaml.iq.volunteer.utils.toTimeString
import kotlinx.android.synthetic.main.fragment_add_campaign.*
import kotlinx.android.synthetic.main.myactivity_not_sign_in.*
import org.jetbrains.anko.design.indefiniteSnackbar
import java.util.*
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 14/03/2018.
 */
class EditCampaignFragment : AddCampaignFragment(), EditCampaignMvpView {

    @Inject
    lateinit var mEditPresenter: EditCampaignMvpPresenter<EditCampaignMvpView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.edit_campaign)
    }

    companion object {
        fun newInstance(args: Bundle = Bundle.EMPTY) = EditCampaignFragment().apply { arguments = args  }
        val TAG: String = EditCampaignFragment::class.java.simpleName
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        getActivityComponent()?.let {
            it.inject(this)
            mEditPresenter.onAttach(this)
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }


    override fun setup(view: View) {
        super.setup(view)

        arguments?.let {
            val campaignId = it.getString(CampaignDetailFragment.BUNDLE_KEY_CAMPAIGN_ID)
            mEditPresenter.setCampaignId(campaignId)
            mEditPresenter.loadCampaignDetail()
        }
    }

    override fun showCampaignDetail(campaignInfo: FbCampaign) {

        titleField.setText(campaignInfo.title)
        setStartDate(campaignInfo.startDate)
        descriptionField.setText(campaignInfo.description)
        // map goes here
        mLocation = campaignInfo.location
        mMapView.getMapAsync(this)
        ageField.setText(campaignInfo.age.toString())
        maxMemberField.setText(campaignInfo.maxMemberCount.toString())


        val volunteerGenderItems = resources.getStringArray(R.array.volunteer_gender).toMutableList()
        mEditPresenter.updateVolunteerGenderItems(volunteerGenderItems, campaignInfo.gender)
        spinnerGender.setItemsArray(volunteerGenderItems)


        val imgRef = FirebaseStorage.getInstance().getReference(AppConstants.CAMPAIGN_IMG_FOLDER  + "/" + campaignInfo.imgName)

        GlideApp.with(this)
                .load(imgRef)
                .signature(ObjectKey(campaignInfo.lastModificationDate))
                .placeholder(R.drawable.campaign_placeholder_img)
                .into(imgCampaign)

    }

    private fun setStartDate(date: Date) {

        startDateField.setText(date.toDateString())
        startTimeField.setText(date.toTimeString())

        val calendar = Calendar.getInstance()
        calendar.time = date
        mSelectedDate = SelectedDate(calendar[Calendar.YEAR], calendar[Calendar.MONTH], calendar[Calendar.DAY_OF_MONTH])
        mSelectedTime = SelectedTime(calendar[Calendar.HOUR], calendar[Calendar.MINUTE])

    }

    override fun onLoadCampaignError() {
        indefiniteSnackbar(imgView, R.string.some_error, R.string.retry) {
            mEditPresenter.loadCampaignDetail()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mEditPresenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {


            R.id.action_done -> {
                val name = titleField.text.toString()
                val description = descriptionField.text.toString()
                val gender = mVolunteersGender
                val age = ageField.text.toString()
                val maxMember = maxMemberField.text.toString()

                mEditPresenter.onActionDoneClick(name, mSelectedTime, mSelectedDate, mLocation, description, gender, age.toIntOrNull(), maxMember.toIntOrNull())

            }
        }

        return false
    }

    override fun getSyncGoogleMap() {
        // just to prevent from loading default
    }

}