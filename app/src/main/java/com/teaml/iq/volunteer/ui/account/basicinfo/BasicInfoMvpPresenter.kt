package com.teaml.iq.volunteer.ui.account.basicinfo

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.MvpPresenter
import java.util.*

/**
 * Created by Mahmood Ali on 06/02/2018.
 */
interface BasicInfoMvpPresenter<V : BasicInfoMvpView> : MvpPresenter<V> {
    fun onDoneClick(name: String, gender: DataManager.UserGender, birthOfDate: Date?)
}