package com.teaml.iq.volunteer.di.module

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.teaml.iq.volunteer.di.annotation.ActivityContext
import com.teaml.iq.volunteer.di.annotation.PerActivity
import com.teaml.iq.volunteer.ui.account.AccountMvpPresenter
import com.teaml.iq.volunteer.ui.account.AccountMvpView
import com.teaml.iq.volunteer.ui.account.AccountPresenter
import com.teaml.iq.volunteer.ui.account.basicinfo.BasicInfoMvpPresenter
import com.teaml.iq.volunteer.ui.account.basicinfo.BasicInfoMvpView
import com.teaml.iq.volunteer.ui.account.basicinfo.BasicInfoPresenter
import com.teaml.iq.volunteer.ui.account.forget.password.ForgetPasswordMvpPresenter
import com.teaml.iq.volunteer.ui.account.forget.password.ForgetPasswordMvpView
import com.teaml.iq.volunteer.ui.account.forget.password.ForgetPasswordPresenter
import com.teaml.iq.volunteer.ui.account.forget.password.emailsend.EmailSendSuccessfullyMvpPresenter
import com.teaml.iq.volunteer.ui.account.forget.password.emailsend.EmailSendSuccessfullyMvpView
import com.teaml.iq.volunteer.ui.account.forget.password.emailsend.EmailSendSuccessfullyPresenter
import com.teaml.iq.volunteer.ui.account.signin.SignInMvpPresenter
import com.teaml.iq.volunteer.ui.account.signin.SignInMvpView
import com.teaml.iq.volunteer.ui.account.signin.SignInPresenter
import com.teaml.iq.volunteer.ui.account.signup.SignUpMvpPresenter
import com.teaml.iq.volunteer.ui.account.signup.SignUpMvpView
import com.teaml.iq.volunteer.ui.account.signup.SignUpPresenter
import com.teaml.iq.volunteer.ui.main.BottomBarAdapter
import com.teaml.iq.volunteer.ui.main.MainMvpPresenter
import com.teaml.iq.volunteer.ui.main.MainMvpView
import com.teaml.iq.volunteer.ui.main.MainPresenter
import com.teaml.iq.volunteer.ui.main.group.GroupAdapter
import com.teaml.iq.volunteer.ui.main.group.GroupMvpPresenter
import com.teaml.iq.volunteer.ui.main.group.GroupMvpView
import com.teaml.iq.volunteer.ui.main.group.GroupPresenter
import com.teaml.iq.volunteer.ui.main.home.CampaignAdapter
import com.teaml.iq.volunteer.ui.main.home.HomeMvpPresenter
import com.teaml.iq.volunteer.ui.main.home.HomeMvpView
import com.teaml.iq.volunteer.ui.main.home.HomePresenter
import com.teaml.iq.volunteer.ui.main.myaccount.MyAccountMvpPresenter
import com.teaml.iq.volunteer.ui.main.myaccount.MyAccountMvpView
import com.teaml.iq.volunteer.ui.main.myaccount.MyAccountPresenter
import com.teaml.iq.volunteer.ui.main.myactivity.MyActivityMvpPresenter
import com.teaml.iq.volunteer.ui.main.myactivity.MyActivityMvpView
import com.teaml.iq.volunteer.ui.main.myactivity.MyActivityPresenter
import com.teaml.iq.volunteer.ui.profile.ProfileMvpPresenter
import com.teaml.iq.volunteer.ui.profile.ProfileMvpView
import com.teaml.iq.volunteer.ui.profile.ProfilePresenter
import com.teaml.iq.volunteer.ui.profile.info.ProfileInfoMvpPresenter
import com.teaml.iq.volunteer.ui.profile.info.ProfileInfoMvpView
import com.teaml.iq.volunteer.ui.profile.info.ProfileInfoPresenter
import com.teaml.iq.volunteer.ui.splash.SplashMvpPresenter
import com.teaml.iq.volunteer.ui.splash.SplashMvpView
import com.teaml.iq.volunteer.ui.splash.SplashPresenter
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
    @ActivityContext
    fun provideContext(): Context = activity

    @Provides
    @PerActivity
    fun provideSplashPresenter(presenter: SplashPresenter<SplashMvpView> ): SplashMvpPresenter<SplashMvpView> =
            presenter

    @Provides
    @PerActivity
    fun provideAccountPresenter(presenter: AccountPresenter<AccountMvpView>): AccountMvpPresenter<AccountMvpView> =
            presenter

    @Provides
    fun provideSignInPresenter(presenter: SignInPresenter<SignInMvpView>): SignInMvpPresenter<SignInMvpView> =
            presenter

    @Provides
    fun provideSignUpPresenter(presenter: SignUpPresenter<SignUpMvpView>): SignUpMvpPresenter<SignUpMvpView> =
            presenter

    @Provides
    fun provideForgetPasswordPresenter(presenter: ForgetPasswordPresenter<ForgetPasswordMvpView>)
            : ForgetPasswordMvpPresenter<ForgetPasswordMvpView>  = presenter
    @Provides
    fun provideEmailSendPresenter(presenter: EmailSendSuccessfullyPresenter<EmailSendSuccessfullyMvpView>)
            : EmailSendSuccessfullyMvpPresenter<EmailSendSuccessfullyMvpView> = presenter

    @Provides
    fun provideBasicInfoPresenter(presenter: BasicInfoPresenter<BasicInfoMvpView>)
            : BasicInfoMvpPresenter<BasicInfoMvpView> = presenter

    /**
     * Main Activity
     */

    @Provides
    @PerActivity
    fun provideMainPresenter(presenter: MainPresenter<MainMvpView>): MainMvpPresenter<MainMvpView> =
            presenter

    @Provides
    fun provideHomePresenter(presenter: HomePresenter<HomeMvpView>): HomeMvpPresenter<HomeMvpView> =
            presenter

    @Provides
    fun provideGroupPresenter(presenter: GroupPresenter<GroupMvpView>)
            : GroupMvpPresenter<GroupMvpView> = presenter

    @Provides
    fun provideBottomBarAdapter(activity: AppCompatActivity) = BottomBarAdapter(activity.supportFragmentManager)

    @Provides
    fun provideLinearLayout(activity: AppCompatActivity) = LinearLayoutManager(activity)

    @Provides
    fun provideCampaignAdapter() = CampaignAdapter(mutableListOf())

    @Provides
    fun provideGroupAdapter() = GroupAdapter(mutableListOf())


    @Provides
    fun provideMyActivityPresenter(presenter: MyActivityPresenter<MyActivityMvpView>): MyActivityMvpPresenter<MyActivityMvpView> =
            presenter

    @Provides
    fun provideMyAccountPresenter(presenter: MyAccountPresenter<MyAccountMvpView>): MyAccountMvpPresenter<MyAccountMvpView> =
            presenter

    @Provides
    fun provideUserProfilePresenter(presenter: ProfileInfoPresenter<ProfileInfoMvpView>): ProfileInfoMvpPresenter<ProfileInfoMvpView> =
            presenter

    @Provides
    fun  providesProfilePresetner(presenter: ProfilePresenter<ProfileMvpView>): ProfileMvpPresenter<ProfileMvpView> =
            presenter



}