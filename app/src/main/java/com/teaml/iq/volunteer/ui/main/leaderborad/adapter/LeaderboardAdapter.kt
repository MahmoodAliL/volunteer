package com.teaml.iq.volunteer.ui.main.leaderborad.adapter

import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.data.model.TopUser
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.BaseViewHolder
import com.teaml.iq.volunteer.utils.AppConstants
import com.teaml.iq.volunteer.utils.clearText
import com.teaml.iq.volunteer.utils.gone
import com.teaml.iq.volunteer.utils.visible
import org.jetbrains.anko.find

/**
 * Created by Mahmood Ali on 29/03/2018.
 */
class LeaderboardAdapter(topUserList: MutableList<TopUser>) : BaseRecyclerAdapter<TopUser>(topUserList) {

    private var onViewItemClick: ((uid: String) -> Unit)? = null

    private var uid: String = ""

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.top_user_view, parent, false)
        return TopUserVH(view)
    }

    fun setUserId(uid: String) {
        this.uid = uid
    }

    fun setOnViewItemClickListener(onViewItemClick: (uid: String) -> Unit) {
        this.onViewItemClick = onViewItemClick
    }

    inner class TopUserVH(val view: View) : BaseViewHolder(view) {

        private val mContext = view.context
        // bind view
        private val txtUserName = view.find<TextView>(R.id.txtUserName)
        private val txtXpPoint = view.find<TextView>(R.id.txtXpPoint)
        private val txtUserSequence = view.find<TextView>(R.id.txtUserSequence)
        private val userImgView = view.find<ImageView>(R.id.userImgView)
        private val viewMyProfile = view.find<View>(R.id.viewMyProfile)


        init {
            view.setOnClickListener {
                val uid = mList[adapterPosition].uid
                onViewItemClick?.invoke(uid)
            }
        }

        override fun clear() {

            view.setBackgroundColor(Color.TRANSPARENT)

            txtUserName.clearText()
            txtXpPoint.clearText()
            txtUserSequence.clearText()
            viewMyProfile.gone
            userImgView.setImageDrawable(null)
        }

        override fun onBind(position: Int) {
            super.onBind(position)

            val topUser = mList[position]

            // if user is user id
            if (topUser.uid == uid) {
                viewMyProfile.visible
                view.setBackgroundColor(ContextCompat.getColor(mContext, R.color.color_my_profile))
            }

            txtUserSequence.text = (position + 1).toString()
            txtUserName.text = topUser.name
            txtXpPoint.text = mContext.getString(R.string.xp_point, topUser.xpPoint)

            val imgRef = FirebaseStorage.getInstance().getReference(AppConstants.USER_IMG_FOLDER + "/" + topUser.imgName )

            GlideApp.with(mContext)
                    .load(imgRef)
                    .placeholder(R.drawable.profile_placeholder_img)
                    .signature(ObjectKey(topUser.lastModificationDate))
                    .into(userImgView)

        }

    }


}