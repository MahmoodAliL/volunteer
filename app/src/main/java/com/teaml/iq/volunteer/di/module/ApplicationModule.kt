package com.teaml.iq.volunteer.di.module

import android.app.Application
import dagger.Module
import dagger.Provides

/**
 * Created by ali on 1/19/2018.
 */

@Module
class ApplicationModule(val application: Application) {

    @Provides
    fun provideApplication() = application

    @Provides
    fun provideContext() = application


}