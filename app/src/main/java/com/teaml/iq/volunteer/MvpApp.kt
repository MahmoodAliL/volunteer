package com.teaml.iq.volunteer

import android.support.multidex.MultiDexApplication
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.di.component.ApplicationComponent
import com.teaml.iq.volunteer.di.component.DaggerApplicationComponent
import com.teaml.iq.volunteer.di.module.ApplicationModule
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 30/01/2018.
 */
class MvpApp : MultiDexApplication() {

    @Inject
    lateinit var mDataManger: DataManager

    lateinit var applicationComponent: ApplicationComponent
        private set

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()

        applicationComponent.inject(this)
    }




}