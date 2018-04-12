package com.teaml.iq.volunteer.ui.campaign.edit

import com.teaml.iq.volunteer.ui.campaign.add.AddCampaignMvpPresenter

/**
 * Created by Mahmood Ali on 14/03/2018.
 */
interface EditCampaignMvpPresenter<V : EditCampaignMvpView> : AddCampaignMvpPresenter<V> {

    fun setCampaignId(campaignId: String)

    fun setComeFrom(comeFrom: Int)

    fun updateVolunteerGenderItems(volunteerGenderItem : MutableList<String>, currentGender: Int)

    fun loadCampaignDetail()

}