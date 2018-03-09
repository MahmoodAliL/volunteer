package com.teaml.iq.volunteer.ui.campaign.add

import android.content.Intent
import com.google.firebase.firestore.GeoPoint
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.SelectedDate
import com.teaml.iq.volunteer.data.model.SelectedTime
import com.teaml.iq.volunteer.ui.base.MvpPresenter

/**
 * Created by ali on 2/25/2018.
 */
interface AddCampaignMvpPresenter<V: AddCampaignMvpView> : MvpPresenter<V> {

    fun onActionDoneClick(name: String, selectedTime: SelectedTime?, selectedDate: SelectedDate?, location: GeoPoint, description: String, gender: DataManager.UserGender, age: Int?, maxMembers: Int?)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)

    fun onRequestReadExternalStoragePermission()

    fun onImgClick()

}