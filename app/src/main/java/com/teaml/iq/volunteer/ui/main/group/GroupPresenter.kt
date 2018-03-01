package com.teaml.iq.volunteer.ui.main.group

import android.util.Log
import com.teaml.iq.volunteer.data.DataManager
import com.teaml.iq.volunteer.data.model.FbGroup
import com.teaml.iq.volunteer.data.model.GroupInfo
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDatePresenter
import javax.inject.Inject

/**
 * Created by ali on 2/4/2018.
 */
class GroupPresenter<V : GroupMvpView> @Inject constructor(dataManager: DataManager)
    : BaseLoadDatePresenter<V>(dataManager), GroupMvpPresenter<V> {


    companion object {
        val TAG: String = GroupPresenter::class.java.simpleName
    }

    override fun loadListData() {
        val groups = mutableListOf<GroupInfo>()

        showProgress()

        dataManager.loadGroupList(this.lastVisibleItem).addOnCompleteListener { task ->
            // before everything hide progress

            onLoadComplete()

            if (task.isSuccessful) {

                val result = task.result

                if (result.isEmpty)
                    return@addOnCompleteListener

                // used for pagination
                lastVisibleItem = result.documents[result.documents.size - 1]

                val fbGroup = result.toObjects(FbGroup::class.java)

                val listOfGroupInfo = fbGroup.mapIndexedTo(groups) { index, value ->
                    GroupInfo(
                            id = result.documents[index].id,
                            name = value.name,
                            groupImg = value.logoImg,
                            campaignsNum = value.campaignsNum,
                            memberNumber = 0,
                            lastModificationDate = value.lastModificationDate)
                }

                mvpView?.updateGroups(listOfGroupInfo)


            } else {
                Log.e(TAG, "${task.exception?.message}")
                onLoadError()
            }

        }

    }





}