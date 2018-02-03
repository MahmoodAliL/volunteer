package com.teaml.iq.volunteer.ui.account

import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.ui.base.BasePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 03/02/2018.
 */
class AccountPersenter<V : AccountMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , AccountMvpPersenter<V> {


}