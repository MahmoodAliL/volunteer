package com.teaml.iq.volunteer.ui.profile.info

import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.ui.base.BasePresenter
import com.teaml.iq.volunteer.utils.toTimestampString
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

                    view.showProfileInfo(profileInfo)

                    if (profileInfo.img.isNotEmpty()) {
                        view.updateProfileImg(profileInfo.img, profileInfo.lastModificationDate.toTimestampString())
                    }

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

        if (mvpView?.isNetworkConnection() == false) {
            mvpView?.onError(R.string.connection_error)
            return
        }

        uid?.let {
            mvpView?.showEditProfileInfoFragment(it)
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