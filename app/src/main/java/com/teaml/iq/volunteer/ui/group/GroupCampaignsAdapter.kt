package com.teaml.iq.volunteer.ui.group

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.data.model.GroupCampaigns
import com.teaml.iq.volunteer.ui.base.BaseViewHolder
import com.teaml.iq.volunteer.utils.AppConstants
import org.jetbrains.anko.find

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
class GroupCampaignsAdapter(val mList: MutableList<GroupCampaigns>) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        val TAG: String = GroupCampaignsAdapter::class.java.simpleName
    }


    inner class GroupCampaignsVH(view: View) : BaseViewHolder(view) {

        private val imgView = view.find<ImageView>(R.id.campaignCoverImg)
        private val txtTitle = view.find<TextView>(R.id.txtCampaignTitle)
        private val txtUploadDate = view.find<TextView>(R.id.txtUploadDate)

        override fun clear() {
            imgView.setImageDrawable(null)
            txtTitle.text = ""
            txtUploadDate.text = ""
        }

        override fun onBind(position: Int) {
            super.onBind(position)

            val item = mList[position]
            txtTitle.text = item.title
            txtUploadDate.text = item.uploadDate

            try {

                val imgRef = FirebaseStorage.getInstance().getReference("${AppConstants.CAMPAIGN_IMG_FOLDER}/${item.imgName}")
                GlideApp.with(txtTitle.context)
                        .load(imgRef)
                        .placeholder(R.drawable.campaign_placeholder_img)
                        .into(imgView)
            } catch (e: Exception) {
                Log.d(TAG, "on loading campaign image", e)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.group_campaigns_view, parent, false)
        return GroupCampaignsVH(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        holder?.onBind(position)
    }

}