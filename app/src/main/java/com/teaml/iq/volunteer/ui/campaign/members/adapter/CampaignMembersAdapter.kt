package com.teaml.iq.volunteer.ui.campaign.members.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.CampaignMembers
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.BaseViewHolder
import com.teaml.iq.volunteer.ui.profile.ProfileActivity
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.CommonUtils
import com.teaml.iq.volunteer.utils.clearText
import com.teaml.iq.volunteer.utils.toTimestamp
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Created by Mahmood Ali on 22/02/2018.
 */
class CampaignMembersAdapter(memberList: MutableList<CampaignMembers>) : BaseRecyclerAdapter<CampaignMembers>(memberList) {


    companion object {
        val TAG: String = CampaignMembersAdapter::class.java.simpleName
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CampaignMembersVH {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.campaign_members_view, parent, false)
        return CampaignMembersVH(v)
    }


    inner class CampaignMembersVH (view: View) : BaseViewHolder(view) {

        private val txtUsername = view.find<TextView>(R.id.txtUserName)
        private val txtJoinDate = view.find<TextView>(R.id.txtJoinDate)
        private val userImgView = view.find<ImageView>(R.id.userImgView)
        private val mContext = view.context


        override fun clear() {
            txtJoinDate.clearText()
            txtUsername.clearText()
            userImgView.setImageDrawable(null)
        }

        override fun onBind(position: Int) {

            val user = mList[position]

            txtUsername.text = user.userName
            txtJoinDate.text = mContext.getString(R.string.join_date, CommonUtils.getHumanReadableElapseTime(user.joinDate, mContext))

            userImgView.setOnClickListener {
                mContext.startActivity<ProfileActivity>(
                        ProfileActivity.EXTRA_KEY_UID to user.uid
                )
            }

            try {

                val imgRef = FirebaseStorage.getInstance().getReference("${AppConstants.USER_IMG_FOLDER}/${user.imgName}")

                GlideApp.with(mContext)
                        .load(imgRef)
                        .signature(ObjectKey(user.lastModificationDate.toTimestamp()))
                        .circleCrop()
                        .placeholder(R.drawable.profile_placeholder_img)
                        .into(userImgView)

            } catch (e: Exception) {
                Log.e(TAG, "on loading image error", e)
            }
        }
    }

}