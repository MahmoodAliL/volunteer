package com.teaml.iq.volunteer.ui.campaign.edit

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.GeoPoint
import com.google.firebase.firestore.SetOptions
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbCampaign
import com.teaml.iq.volunteer.data.model.SelectedDate
import com.teaml.iq.volunteer.data.model.SelectedTime
import com.teaml.iq.volunteer.ui.campaign.add.AddCampaignPresenter
import com.teaml.iq.volunteer.utils.CommonUtils
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 14/03/2018.
 */
class EditCampaignPresenter<V : EditCampaignMvpView> @Inject constructor(dataManager: DataManager) : AddCampaignPresenter<V>(dataManager), EditCampaignMvpPresenter<V> {


    companion object {
        val TAG: String = EditCampaignPresenter::class.java.simpleName

    }

    private var mCampaignId = ""
    private var mCurrentCampaignInfo: FbCampaign? = null


    override fun setCampaignId(campaignId: String) {
        this.mCampaignId = campaignId
    }


    override fun loadCampaignDetail() {
        mvpView?.showLoading()

        dataManager.getCampaignDocRef(mCampaignId).get().addOnCompleteListener {

            mvpView?.hideLoading()
            if (it.isSuccessful) {
                mCurrentCampaignInfo = it.result.toObject(FbCampaign::class.java)

                mvpView?.showCampaignDetail(mCurrentCampaignInfo!!)
            } else {
                mvpView?.onLoadCampaignError()
                Log.e(TAG, "onLoadCampaignInfo", it.exception)
            }
        }

    }

    override fun updateVolunteerGenderItems(volunteerGenderItem: MutableList<String>, currentGender: Int) {

        when (currentGender) {

            DataManager.UserGender.ANY.type -> {
                volunteerGenderItem.removeAt(1)
                volunteerGenderItem.removeAt(1)
            }

            DataManager.UserGender.FEMALE.type -> {
                volunteerGenderItem.removeAt(1)
            }

            DataManager.UserGender.MALE.type -> {
                volunteerGenderItem.removeAt(2)
            }
        }
    }

    override fun onActionDoneClick(name: String, selectedTime: SelectedTime?, selectedDate: SelectedDate?, location: GeoPoint, description: String, gender: DataManager.UserGender, age: Int?, maxMembers: Int?) {

        if (mCurrentCampaignInfo == null) {
            Log.e(TAG, "mCurrentCampaignInfo == null")
            return
        }

        mvpView?.let { view ->

            val uid = dataManager.getFirebaseUserAuthID()

            if (uid == null) {
                view.showMessage(R.string.some_error)
                return
            }

            if (name.isEmpty()) {
                view.onError(R.string.empty_name)
                return
            }

            if (selectedDate == null) {
                view.onError(R.string.please_select_start_date)
                return
            }

            if (selectedTime == null) {
                view.onError(R.string.please_select_start_time)
                return
            }

            if (description.isEmpty()) {
                view.onError(R.string.empty_descriotion)
                return
            }

            if (age == null) {
                view.onError(R.string.please_select_required_age)
                return
            }

            if (maxMembers == null) {
                view.onError(R.string.please_select_max_count_members)
                return
            }

            if (age !in mCurrentCampaignInfo!!.age..100) {
                view.onError(R.string.required_age_should_be_equal_or_greater_then_old_age)
                return
            }
            if (maxMembers !in mCurrentCampaignInfo!!.maxMemberCount..1000) {
                view.onError(R.string.required_max_member_should_be_equal_or_greater_then_old_max_member)
                return
            }

            val startDate = with(selectedDate) {
                CommonUtils.dateTimeFrom(year, month, dayOfMonth, selectedTime.hour, selectedTime.minute)
            }

            campaignInfo.putAll(hashMapOf(
                    FbCampaign::title.name to name,
                    FbCampaign::startDate.name to startDate,
                    FbCampaign::lastModificationDate.name to FieldValue.serverTimestamp(),
                    FbCampaign::description.name to description,
                    FbCampaign::gender.name to gender.type,
                    FbCampaign::location.name to location,
                    FbCampaign::age.name to age,
                    FbCampaign::maxMemberCount.name to maxMembers,
                    FbCampaign::isEdited.name to true
            ))

            view.showLoading()
            if (imgUri != null) {
                uploadImg()
                return
            }

            uploadOtherInfo()
        }
    }


    override fun uploadOtherInfo() {

        val uid = dataManager.getFirebaseUserAuthID()

        if (uid == null) {
            mvpView?.onError(R.string.some_error)
            Log.e(TAG, "on uid null")
            return
        }

        dataManager.getCampaignDocRef(mCampaignId).set(campaignInfo, SetOptions.merge()).addOnCompleteListener {
            mvpView?.hideLoading()
            if (it.isSuccessful) {
                mvpView?.showMyGroupFragment(uid)
            } else {
                mvpView?.onError(R.string.some_error)
                Log.e(TAG, "on update campaign ", it.exception)
            }
        }
    }


}