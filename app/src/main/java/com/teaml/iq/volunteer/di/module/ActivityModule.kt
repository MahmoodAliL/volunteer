package com.teaml.iq.volunteer.di.module

import android.app.DatePickerDialog
import android.content.Context
import android.support.v7.app.AppCompatActivity
import com.teaml.iq.volunteer.di.annotation.ActivityContext
import com.teaml.iq.volunteer.di.annotation.PerActivity
import com.teaml.iq.volunteer.ui.account.AccountMvpPresenter
import com.teaml.iq.volunteer.ui.account.AccountMvpView
import com.teaml.iq.volunteer.ui.account.AccountPresenter
import com.teaml.iq.volunteer.ui.account.basicinfo.*
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
import java.util.*

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

    @Provides
    fun provideDatePickerDialog(appclass: AppClass ): DatePickerDialog {

        val datePickerDialog = DatePickerDialog(activity, appclass, 1999, 9, 9)

        val calender = Calendar.getInstance()
        val currentYear = calender.get(Calendar.YEAR)
        // change  only user greater than  16 old is accepted
        calender.set(Calendar.YEAR, currentYear - 16)
        val maxDate = calender.timeInMillis

        // only user smaller then 100 year is accepted
        calender.set(Calendar.YEAR, currentYear - 100)
        val minDate = calender.timeInMillis

        datePickerDialog.datePicker.maxDate =  maxDate
        datePickerDialog.datePicker.minDate = minDate
        return datePickerDialog
    }
}