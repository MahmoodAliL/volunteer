package com.teaml.iq.volunteer.di.module

import android.app.Application
import android.content.Context
import com.teaml.iq.volunteer.data.AppDataManager
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.di.annotation.ApplicationContext
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
    fun dataManager(appDataManager: AppDataManager): DataManager = appDataManager

}