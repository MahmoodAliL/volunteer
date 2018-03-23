package com.teaml.iq.volunteer.ui.campaign.members.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.CampaignMembers
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.data.model.RateMembers
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.BaseViewHolder
import com.teaml.iq.volunteer.utils.*
import org.jetbrains.anko.find

/**
 * Created by ali on 3/16/2018.
 */
class RateMemberAdapter(memberList: MutableList<RateMembers>): BaseRecyclerAdapter<RateMembers>(memberList) {

    var onItemClick:((String)->Unit)? = null
    var onHelpfulClick:((String,Int)->Unit)? = null
    var onUnhelpfulClick:((String,Int)->Unit)? = null
    var onNotAttendClick:((String,Int)->Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingMemberVH {
        val viewRow = LayoutInflater.from(parent.context).inflate( R.layout.rate_member_view,parent,false)
        return RatingMemberVH(viewRow)

    }

    fun onItemRemove(position: Int) {
        mList.removeAt(position)
        notifyItemRemoved(position)
    }

    fun onItemClickListener(listener:((String)-> Unit)) {
        onItemClick = listener
    }
    fun onHelpfulClickListener(listener: (String,Int) -> Unit) {
        onHelpfulClick = listener
    }
    fun onUnhelpfulClickListener(listener: (String,Int)-> Unit) {
        onUnhelpfulClick = listener
    }
    fun onNotAttendClickListener(listener: (String, Int) -> Unit) {
        onNotAttendClick = listener
    }

    inner class RatingMemberVH(val view: View): BaseViewHolder(view) {
        private val memberName = view.find<TextView>(R.id.txtMemberName)
        val joinDate = view.find<TextView>(R.id.txtJoinDate)
        private val memberImg = view.find<ImageView>(R.id.memberImg)
        private val helpful = view.find<ImageView>(R.id.imgHelpful)
        private val imgMenu = view.find<ImageView>(R.id.imgMenu)
        private val whiteScreen = view.find<View>(R.id.whiteScreen)
        private val mContext = view.context

        init {

            helpful.setOnClickListener {
                whiteScreen.visible
                onHelpfulClick?.invoke(mList[currentPosition].uid,currentPosition)
            }

            val popupMenu = PopupMenu(mContext,imgMenu)
            popupMenu.inflate(R.menu.rate_member_menu)

            imgMenu.setOnClickListener {
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {

                        R.id.action_unhelpful -> {
                            whiteScreen.visible
                            onUnhelpfulClick?.invoke(mList[currentPosition].uid,currentPosition)
                        }

                        R.id.action_not_present -> {
                            whiteScreen.visible
                            onNotAttendClick?.invoke(mList[currentPosition].uid,currentPosition)
                        }
                    }
                    true
                }
                popupMenu.show()
            }
        }
        override fun clear() {
            memberName.text = ""
            joinDate.text = ""
            memberImg.setImageDrawable(null)
        }

        override fun onBind(position: Int) {

            val member = mList[position]

            memberName.text = member.userName
            joinDate.text = mContext.getString(R.string.join_date,CommonUtils.getHumanReadableElapseTime(member.joinDate,mContext))

            try {
                val imgRef = FirebaseStorage.getInstance().getReference("${AppConstants.USER_IMG_FOLDER}/${member.imgName}")
                GlideApp.with(mContext)
                        .load(imgRef)
                        .signature(ObjectKey{member.lastModificationDate.toTimestampString()})
                        .placeholder(R.drawable.profile_placeholder_img)
                        .circleCrop()
                        .into(memberImg)
            }catch (e:Exception){

            }

        }

    }

}