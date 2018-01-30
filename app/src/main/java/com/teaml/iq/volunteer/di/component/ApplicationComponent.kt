package com.teaml.iq.volunteer.di.component

import android.app.Application
import android.content.Context
import com.teaml.iq.volunteer.MvpApp
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.di.annotation.ApplicationContext
import com.teaml.iq.volunteer.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Mahmood Ali on 30/01/2018.
 */
@Singleton
@Component(modules = [(ApplicationModule::class)])
interface ApplicationComponent {

    fun inject(app: MvpApp)

    @ApplicationContext
    fun context(): Context

    fun application(): Application

    fun dataManager(): DataManager


}