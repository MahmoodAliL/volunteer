package com.teaml.iq.volunteer.ui.group.edit

import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldValue
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.ui.group.create.CreateGroupPresenter
import com.teaml.iq.volunteer.utils.AppConstants
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 05/03/2018.
 *
 */
class EditGroupPresenter<V : EditGroupMvpView> @Inject constructor(dataManager: DataManager)
    : CreateGroupPresenter<V>(dataManager), EditGroupMvpPresenter<V> {


    override fun initGroupInfo(name: String, bio: String) {
        groupInfo[FbGroup::name.name] = name
        groupInfo[FbGroup::bio.name] = bio
        groupInfo[FbGroup::lastModificationDate.name] = FieldValue.serverTimestamp()

    }

    override fun onActionDoneClick(name: String, bio: String) {
        mvpView?.let { view ->
            if (name.isEmpty()) {
                view.onError(R.string.empty_name)
                return
            }

            if (bio.isEmpty()) {
                view.onError(R.string.empty_name)
                return
            }

            initGroupInfo(name, bio)

            // TODO: check if image is uploaded then does't uploaded
            if (imgCoverUri != null) {
                super.uploadCoverImg()
                return
            }

            if (imgLogoUri != null ) {
                mvpView?.showLoading()
                super.uploadLogoImg()
                return
            }

            mvpView?.showLoading()
            super.uploadOtherInfo()

        }
    }
    override fun loadGroupDetail() {

        val uid = dataManager.getFirebaseUserAuthID()

        if (uid == null) {
            mvpView?.onError(R.string.some_error)
            return
        }

        mvpView?.showLoading()

        doAsync {

            try {
                // wait group info to get result
                val groupInfoTask = dataManager.getGroupDocRef(uid).get()
                Tasks.await(groupInfoTask, AppConstants.REQUEST_LONG_TIME_OUT, TimeUnit.SECONDS)
                // get result of group info
                val group: FbGroup = groupInfoTask.result.toObject(FbGroup::class.java)

                uiThread { mvpView?.showGroupDetail(group) }

            } catch (e: Exception) {
                Log.e(TAG, "on load group detail", e)
                uiThread { mvpView?.onLoadGroupInfoError() }
            } finally {
                uiThread { mvpView?.hideLoading() }
            }

        }
    }


    override fun onUploadSuccess() {
        val uid = dataManager.getFirebaseUserAuthID()

        if (uid == null) {
            mvpView?.onError(R.string.some_error)
            return
        }

        mvpView?.showGroupDetailFragment(uid)
    }

}