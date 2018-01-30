package com.teaml.iq.volunteer.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.teaml.iq.volunteer.data.AppDataManager
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.di.annotation.ActivityContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ali on 1/19/2018.
 */

@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    fun provideActivity() = activity

    @Provides
    @ActivityContext
    fun provideContext(): Context = activity




}