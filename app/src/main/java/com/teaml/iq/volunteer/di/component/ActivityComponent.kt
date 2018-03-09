package com.teaml.iq.volunteer.di.component

import com.teaml.iq.volunteer.di.annotation.PerActivity
import com.teaml.iq.volunteer.di.module.ActivityModule
import com.teaml.iq.volunteer.ui.account.AccountActivity
import com.teaml.iq.volunteer.ui.account.basicinfo.BasicInfoFragment
import com.teaml.iq.volunteer.ui.account.forget.password.ForgetPasswordFragment
import com.teaml.iq.volunteer.ui.account.forget.password.emailsend.EmailSendSuccessfullyFragment
import com.teaml.iq.volunteer.ui.account.signin.SignInFragment
import com.teaml.iq.volunteer.ui.account.signup.SignUpFragment
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import com.teaml.iq.volunteer.ui.campaign.add.AddCampaignFragment
import com.teaml.iq.volunteer.ui.campaign.detail.CampaignDetailFragment
import com.teaml.iq.volunteer.ui.campaign.members.CampaignMembersFragment
import com.teaml.iq.volunteer.ui.group.GroupActivity
import com.teaml.iq.volunteer.ui.group.create.CreateGroupFragment
import com.teaml.iq.volunteer.ui.group.detail.GroupDetailFragment
import com.teaml.iq.volunteer.ui.group.edit.EditGroupFragment
import com.teaml.iq.volunteer.ui.group.view_all_campaign.GroupCampaignsFragment
import com.teaml.iq.volunteer.ui.main.MainActivity
import com.teaml.iq.volunteer.ui.main.group.GroupsFragment
import com.teaml.iq.volunteer.ui.main.home.HomeFragment
import com.teaml.iq.volunteer.ui.main.myaccount.MyAccountFragment
import com.teaml.iq.volunteer.ui.main.myactivity.MyActivityFragment
import com.teaml.iq.volunteer.ui.profile.ProfileActivity
import com.teaml.iq.volunteer.ui.profile.edit.EditProfileFragment
import com.teaml.iq.volunteer.ui.profile.info.ProfileInfoFragment
import com.teaml.iq.volunteer.ui.splash.SplashActivity
import dagger.Component

/**
 * Created by Mahmood Ali on 30/01/2018.
 */

@PerActivity
@Component(modules = [(ActivityModule::class)], dependencies = [ApplicationComponent::class])
interface ActivityComponent {

    fun inject(activity: SplashActivity)

    fun inject(activity: AccountActivity)

    fun inject(activity: MainActivity)

    fun inject(activity: ProfileActivity)

    fun inject(activity: CampaignActivity)

    fun inject(activity: GroupActivity)

    fun inject(fragment: SignInFragment)

    fun inject(fragment: SignUpFragment)

    fun inject(fragment: EmailSendSuccessfullyFragment)

    fun inject(fragment: ForgetPasswordFragment)

    fun inject(fragment: BasicInfoFragment)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: GroupsFragment)

    fun inject(fragment: MyActivityFragment)

    fun inject(fragment: MyAccountFragment)

    fun inject(fragment: ProfileInfoFragment)

    fun inject(fragment: EditProfileFragment)

    fun inject(fragment: CampaignDetailFragment)

    fun inject(fragment: CampaignMembersFragment)

    fun inject(fragment: GroupDetailFragment)

    fun inject(fragment: GroupCampaignsFragment)

    fun inject(fragment: CreateGroupFragment)

    fun inject(fragment: EditGroupFragment)

    fun inject(fragment: AddCampaignFragment)

}