package com.teaml.iq.volunteer.ui.main.leaderborad

import android.os.Bundle
import android.view.View
import com.teaml.iq.volunteer.data.model.TopUser
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.loadata.BaseLoadDataFragment
import com.teaml.iq.volunteer.ui.main.leaderborad.adapter.LeaderboardAdapter
import com.teaml.iq.volunteer.ui.profile.ProfileActivity
import org.jetbrains.anko.startActivity
import javax.inject.Inject

/**
 * Created by Mahmood Ali on 29/03/2018.
 */
class LeaderboardFragment : BaseLoadDataFragment<TopUser>(), LeaderboardMvpView {

    companion object {
        val TAG: String = LeaderboardFragment::class.java.simpleName
        fun newInstance(args: Bundle = Bundle.EMPTY) = LeaderboardFragment().apply { arguments = args }
    }

    @Inject
    lateinit var mPresenter: LeaderboardMvpPresenter<LeaderboardMvpView>

    @Inject
    lateinit var mLeaderboardAdapter: LeaderboardAdapter

    override fun initActivityComponent() {
        getActivityComponent()?.let {
            it.inject(this)
            mPresenter.onAttach(this)
        }
    }

    override fun setup(view: View) {

        mLeaderboardAdapter.setOnViewItemClickListener {
            mPresenter.onViewItemClick(it)
        }

        mPresenter.onViewPrepared()

    }

    override fun openProfileActivity(uid: String) {
        context?.startActivity<ProfileActivity>(ProfileActivity.EXTRA_KEY_UID to uid)
    }

    override fun setUserId(uid: String) {
        mLeaderboardAdapter.setUserId(uid)
    }

    override fun addNewItems(listOfTopUser: List<TopUser>) {
        mLeaderboardAdapter.addItems(listOfTopUser)
    }

    override fun onCreateRecyclerAdapter(): BaseRecyclerAdapter<TopUser> = mLeaderboardAdapter

    override fun onDestroyView() {
        mPresenter.onDetach()
        super.onDestroyView()
    }

}