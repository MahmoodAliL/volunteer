package com.teaml.iq.volunteer.ui.main.leaderborad

import android.util.Log
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbUserDetail
import com.teaml.iq.volunteer.data.model.TopUser
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDatePresenter
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 29/03/2018.
 */
class LeaderboardPresenter<V : LeaderboardMvpView> @Inject constructor(dataManager: DataManager)
    : BaseLoadDatePresenter<V>(dataManager), LeaderboardMvpPresenter<V> {

    companion object {
        val TAG: String = LeaderboardPresenter::class.java.simpleName
    }

    override fun onViewPrepared() {
        super.onViewPrepared()
        val uid = dataManager.getFirebaseUserAuthID()
        if (uid != null)
            mvpView?.setUserId(uid)
    }


    override fun onViewItemClick(uid: String) {
        mvpView?.openProfileActivity(uid)
    }

    override fun loadListData() {

        Log.d(TAG, "onLoadTopUser loading")
        showProgress()

        dataManager.loadTopUsers(lastVisibleItem).get().addOnCompleteListener {
            Log.d(TAG, "onLoadTopUser completed")
            onLoadComplete()

            if (it.isSuccessful) {

                val result = it.result

                if (result.isEmpty) {
                    // disable load more when user reach to end
                    mvpView?.enableLoadMore(false)
                    return@addOnCompleteListener
                }
                // used for pagination
                lastVisibleItem = result.documents[result.documents.size - 1]

                val fbUserDetailList: MutableList<FbUserDetail> = result.toObjects(FbUserDetail::class.java)

                val topUserList: List<TopUser> = fbUserDetailList.mapIndexed { index, fbUserDetail ->
                    TopUser(uid = result.documents[index].id,
                            name = fbUserDetail.name,
                            imgName = fbUserDetail.img,
                            xpPoint = fbUserDetail.xpPoint,
                            lastModificationDate = fbUserDetail.lastModificationDate)
                }

                mvpView?.addNewItems(topUserList)


            } else {
                Log.e(TAG, "error onLoadTopUserInfo ", it.exception)
                onLoadError()
            }
        }
    }


}