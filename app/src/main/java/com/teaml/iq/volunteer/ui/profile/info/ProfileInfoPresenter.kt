package com.teaml.iq.volunteer.ui.profile.info

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.CommonUtils
import com.teaml.iq.volunteer.utils.toDateString
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 13/02/2018.
 */
class ProfileInfoPresenter<V : ProfileInfoMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager), ProfileInfoMvpPresenter<V> {


    private var uid: String? = null

    companion object {
        val TAG: String = ProfileInfoPresenter::class.java.simpleName
    }

    override fun fetchProfileInfo(uid: String) {
        this.uid = uid
        Log.e(TAG, uid)
        loadProfileInfo(uid)
    }

    private fun loadProfileInfo(uid: String) {

        mvpView?.let { view ->

            view.showProgress()
            view.getBaseActivity()?.let { activity ->
                dataManager.getUserDocRef(uid).addSnapshotListener(activity) { documentSnapshot, firebaseFirestoreException ->

                    if (mvpView == null)
                        return@addSnapshotListener

                    view.hideProgress()

                    if (firebaseFirestoreException != null) {
                        Log.e(TAG, firebaseFirestoreException.message)
                        view.showRetryImg()
                        return@addSnapshotListener
                    }
                    val profileInfo = documentSnapshot.toObject(FbUserDetail::class.java)

                    if (profileInfo.img.isNotEmpty()) {
                        view.updateProfileImg(profileInfo.img, profileInfo.lastImgUpdate)
                    }

                    if (profileInfo.bio.isNotEmpty()) {
                        view.updateUserBio(profileInfo.bio)
                    }

                    if (profileInfo.phone.isNotEmpty())
                        view.updatePhoneNumber(profileInfo.phone)

                    view.updateUserName(profileInfo.name)
                    view.updateBirthOfDay(profileInfo.birthOfDay.toDateString())
                    view.updateGender(CommonUtils.intGenderToString(profileInfo.gender, activity))

                    if (isSameUser(uid))
                        dataManager.getCurrentUserEmail()?.let { email ->
                            view.showAndUpdateEmail(email)
                        }

                }
            }
        }

    }

    override fun onRetryImgClick() {
        uid?.let {
            mvpView?.showProgress()
            mvpView?.hideRetryImg()
            loadProfileInfo(it)

        }
    }

    override fun onActionEditClick() {
        uid?.let {
            mvpView?.showEditProfileInfo(it)
        }
    }

    override fun onCreateOptionMenu(menu: Menu?, inflater: MenuInflater?) {
        uid?.let {
            if (isSameUser(it)) {
                inflater?.inflate(R.menu.my_profile_menu, menu)
            }
        }
    }



}