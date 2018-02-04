package com.teaml.iq.volunteer.di.component

import com.teaml.iq.volunteer.di.annotation.PerActivity
import com.teaml.iq.volunteer.di.module.ActivityModule
import com.teaml.iq.volunteer.di.module.ApplicationModule
import com.teaml.iq.volunteer.ui.account.AccountActivity
import com.teaml.iq.volunteer.ui.account.signin.SigninFragment
import com.teaml.iq.volunteer.ui.main.MainActivity
import com.teaml.iq.volunteer.ui.main.home.HomeFragment
import com.teaml.iq.volunteer.ui.splash.SplashActivity
import dagger.Component

/**
 * Created by Mahmood Ali on 30/01/2018.
 */

@PerActivity
@Component(modules = [(ActivityModule::class)], dependencies = [ApplicationComponent::class])
interface ActivityComponent {

    fun inject(activity: MainActivity)

    fun inject(activity: SplashActivity)

    fun inject(activity: AccountActivity)

    //fun inject(signinFragment: SigninFragment)

    fun inject(homeFragment: HomeFragment)


}