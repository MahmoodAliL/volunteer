package com.teaml.iq.volunteer.di.module

import android.app.Application
import android.content.Context
import com.teaml.iq.volunteer.data.AppDataManager
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.pref.AppPreferenceHelper
import com.teaml.iq.volunteer.data.pref.PreferenceHelper
import com.teaml.iq.volunteer.di.annotation.ApplicationContext
import com.teaml.iq.volunteer.di.annotation.PreferenceName
import com.teaml.iq.volunteer.utils.AppConstants
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by ali on 1/19/2018.
 */

@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication() = application

    @Provides
    @ApplicationContext
    fun provideContext(): Context = application

    @Provides
    @Singleton
    fun provideDataManager(appDataManager: AppDataManager): DataManager = appDataManager

    @Provides
    @PreferenceName
    fun providePrefName(): String = AppConstants.PREF_NAME

    @Provides
    @Singleton
    fun providePreferenceHelper(appPreferenceHelper: AppPreferenceHelper): PreferenceHelper = appPreferenceHelper



}