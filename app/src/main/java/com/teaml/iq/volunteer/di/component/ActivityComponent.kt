package com.teaml.iq.volunteer.di.component

import com.teaml.iq.volunteer.di.annotation.PerActivity
import com.teaml.iq.volunteer.di.module.ActivityModule
import com.teaml.iq.volunteer.di.module.ApplicationModule
import dagger.Component

/**
 * Created by Mahmood Ali on 30/01/2018.
 */

@PerActivity
@Component(modules = [ActivityModule::class], dependencies = [ApplicationComponent::class])
interface ActivityComponent {

}