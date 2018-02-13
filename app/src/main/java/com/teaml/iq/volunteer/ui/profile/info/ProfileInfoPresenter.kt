package com.teaml.iq.volunteer.ui.profile.info

import android.util.Log
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.ui.base.BasePresenter
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
        mvpView?.showProgress()
        mvpView?.getBaseActivity()?.let { activity ->
            dataManager.getUserReference(uid).addSnapshotListener(activity) { documentSnapshot, firebaseFirestoreException ->

                mvpView?.hideProgress()
                if (firebaseFirestoreException != null) {
                    Log.e(TAG, firebaseFirestoreException.message)
                    mvpView?.showRetryImg()
                    return@addSnapshotListener
                }
                val profileInfo = documentSnapshot.toObject(FbUserDetail::class.java)
                mvpView?.showProfileInfo(profileInfo)
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




}