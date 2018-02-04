package com.teaml.iq.volunteer.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.teaml.iq.volunteer.di.annotation.ActivityContext
import com.teaml.iq.volunteer.di.annotation.PerActivity
import com.teaml.iq.volunteer.ui.account.AccountMvpPersenter
import com.teaml.iq.volunteer.ui.account.AccountMvpView
import com.teaml.iq.volunteer.ui.account.AccountPersenter
import com.teaml.iq.volunteer.ui.intro.IntroMvpPresenter
import com.teaml.iq.volunteer.ui.intro.IntroMvpView
import com.teaml.iq.volunteer.ui.intro.IntroPresenter
import com.teaml.iq.volunteer.ui.main.MainMvpPresenter
import com.teaml.iq.volunteer.ui.main.MainMvpView
import com.teaml.iq.volunteer.ui.main.MainPresenter
import com.teaml.iq.volunteer.ui.main.home.HomeMvpPresenter
import com.teaml.iq.volunteer.ui.main.home.HomeMvpView
import com.teaml.iq.volunteer.ui.main.home.HomePresenter
import com.teaml.iq.volunteer.ui.splash.SplashMvpPresenter
import com.teaml.iq.volunteer.ui.splash.SplashMvpView
import com.teaml.iq.volunteer.ui.splash.SplashPresenter
import dagger.Module
import dagger.Provides

/**
 * Created by ali on 1/19/2018.
 */

@Module
class ActivityModule(activity: AppCompatActivity) {

    private val mActivity = activity

    @Provides
    fun provideActivity() = mActivity

    @Provides
    @ActivityContext
    fun provideContext(): Context = mActivity

    @Provides
    @PerActivity
    fun provideMainPresenter(presenter: MainPresenter<MainMvpView>): MainMvpPresenter<MainMvpView> =
            presenter

    @Provides
    fun provideHomePresenter(presenter: HomePresenter<HomeMvpView>): HomeMvpPresenter<HomeMvpView> =
            presenter

    @Provides
    @PerActivity
    fun provideSplashPresenter(presenter: SplashPresenter<SplashMvpView> ): SplashMvpPresenter<SplashMvpView> =
            presenter

    @Provides
    @PerActivity
    fun provideIntroPresenter(presenter: IntroPresenter<IntroMvpView>): IntroMvpPresenter<IntroMvpView> =
            presenter

    @Provides
    @PerActivity
    fun proivdeAccountPresenter(presenter: AccountPersenter<AccountMvpView>): AccountMvpPersenter<AccountMvpView> =
            presenter





}