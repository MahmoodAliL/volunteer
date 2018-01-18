package com.teaml.iq.volunteer.di.module

import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides

/**
 * Created by ali on 1/19/2018.
 */

@Module
class ActivityModule(val activity: AppCompatActivity) {

    @Provides
    fun provideActivity() = activity

    @Provides
    fun provideContext() = activity

}