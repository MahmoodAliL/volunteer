package com.teaml.iq.volunteer.ui.main.group

import android.util.Log
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.GroupPost
import com.teaml.iq.volunteer.ui.base.BasePresenter
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*
import javax.inject.Inject

/**
 * Created by ali on 2/4/2018.
 */
class GroupPresenter<V : GroupMvpView> @Inject constructor(dataManager: DataManager)
    : BasePresenter<V>(dataManager) , GroupMvpPresenter<V> {



    private var pageNumber = 0

    companion object {
        val TAG: String = GroupPresenter::class.java.simpleName
    }

    override fun onViewPrepared() {
        Log.d(TAG, "onViewPrepared")
        loadingDataFromServer()
    }


    override fun onRetryClick() {
        mvpView?.setFieldError(false)
        mvpView?.hideRetryImg()
        loadingDataFromServer()
    }

    fun loadingDataFromServer() {

        mvpView?.showProgress()

        doAsync {
            Thread.sleep(1000)

            uiThread {
                mvpView?.setLoadingMoreDone()
                mvpView?.hideProgress()
            }

            if (Random().nextBoolean()) {
                val groups = mutableListOf<GroupPost>()

                for (i in 1..5) {
                    groups.add(GroupPost(
                            groupImg = "logo1.jpg",
                            name = "Team-L",
                            memberNumber = 3,
                            campaignNumber = 43
                    ))
                }
                uiThread { onComplete(groups)  }

            } else {
                uiThread { onError() }

            }
        }

    }

    fun onComplete(data: MutableList<GroupPost>) {

        if (data.isEmpty() && pageNumber == 0)
            mvpView?.showEmptyResult()
        else {
            pageNumber++
            mvpView?.updateGroups(data)
        }
    }

    fun onError() {
        mvpView?.setFieldError(true)
        mvpView?.showRetryImg()
    }

    override fun onLoadingMore() {
        loadingDataFromServer()
    }

}