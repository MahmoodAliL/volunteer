package com.teaml.iq.volunteer.ui.campaign.members.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.CampaignMembers
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.base.BaseViewHolder
import com.teaml.iq.volunteer.ui.profile.ProfileActivity
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.clearText
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Created by Mahmood Ali on 22/02/2018.
 */

class CampaignMembersVH (view: View) : BaseViewHolder(view) {

    private val txtUsername = view.find<TextView>(R.id.txtUserName)
    private val txtJoinDate = view.find<TextView>(R.id.txtJoinDate)
    private val userImgView = view.find<ImageView>(R.id.userImgView)
    private val mContext = view.context


    companion object {
        val TAG: String = CampaignMembersVH::class.java.simpleName
    }
    override fun clear() {
        txtJoinDate.clearText()
        txtUsername.clearText()
        userImgView.setImageDrawable(null)
    }

    fun onBind(model: CampaignMembers) {
        txtUsername.text = model.userName
        txtJoinDate.text = mContext.getString(R.string.join_date, model.joinDate)

        userImgView.setOnClickListener {
                mContext.startActivity<ProfileActivity>(
                        ProfileActivity.EXTRA_KEY_UID to model.uid
                )
        }

        try {

            val imgRef = FirebaseStorage.getInstance().getReference("${AppConstants.USER_IMG_FOLDER}/${model.imgName}")

            GlideApp.with(mContext)
                    .load(imgRef)
                    .signature(ObjectKey(imgRef.name))
                    .circleCrop()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .placeholder(R.drawable.profile_placeholder_img)
                    .into(userImgView)

        } catch (e: Exception) {
            Log.e(TAG, "on loading image error", e)
        }
    }
}