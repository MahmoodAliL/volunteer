package com.teaml.iq.volunteer.ui.main.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.signature.ObjectKey
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.base.BaseRecyclerAdapter
import com.teaml.iq.volunteer.ui.base.BaseViewHolder
import com.teaml.iq.volunteer.ui.campaign.CampaignActivity
import com.teaml.iq.volunteer.ui.group.GroupActivity
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_IMG_FOLDER
import com.teaml.iq.volunteer.utils.AppConstants.GROUP_LOGO_IMG_FOLDER
import com.teaml.iq.volunteer.utils.CommonUtils
import com.teaml.iq.volunteer.utils.toTimestampString
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

/**
 * Created by Mahmood Ali on 09/02/2018.
 */
class CampaignAdapter(campaignPostList: MutableList<CampaignPost>) : BaseRecyclerAdapter<CampaignPost>(campaignPostList) {


    companion object {
        val TAG: String = CampaignAdapter::class.java.simpleName
    }


    init {
        // because the default is 2
        //setVisibleThreshold(2)
    }

    private var onViewItemClick: ((campaignId: String, groupId: String) -> Unit)? = null

    fun setOnViewItemClickListener(onViewItemClick: ((campaignId: String, groupId: String) -> Unit)) {
        this.onViewItemClick = onViewItemClick
    }

    inner class CampaignVH(itemView: View) : BaseViewHolder(itemView) {

        private val firebaseStorage = FirebaseStorage.getInstance()
        private val mContext = itemView.context

        private val campaignTitle: TextView = itemView.find(R.id.campaignTitle)
        private val txtGroupNameUploadDateViews = itemView.find<TextView>(R.id.txtGroupNameUploadDateViews)
        private val orgImgView = itemView.find<ImageView>(R.id.orgImgView)
        private val campaignCoverImgView = itemView.find<ImageView>(R.id.campaignCoverImg)


        init {
            itemView.setOnClickListener {
                val campaign = mList[currentPosition]
                Log.e(TAG, "onItemClick -> CampaignId: ${campaign.campaignId}, GroupId: ${campaign.groupId}")
                onViewItemClick?.invoke(campaign.campaignId, campaign.groupId)
            }
        }

        override fun clear() {
            campaignTitle.text = ""
            txtGroupNameUploadDateViews.text = ""
            orgImgView.setImageResource(R.drawable.group_logo_placeholder_img)
            campaignCoverImgView.setImageResource(R.drawable.campaign_placeholder_img)
        }

        override fun onBind(position: Int) {
            super.onBind(position)

            val item = mList[position]

            with(item) {

                campaignTitle.text = title
                // if we need to translate some string in text and make concatenating
                // them we should using this way

                txtGroupNameUploadDateViews.text = mContext.getString(
                        R.string.group_name_views_date,
                        groupName,
                        viewsCount,
                        CommonUtils.getHumanReadableElapseTime(uploadDate, mContext)
                )
                /*val temp = "$groupName . ${CommonUtils.getHumanReadableElapseTime(uploadDate, mContext)}"
                txtGroupNameUploadDateViews.text = temp*/

                try {
                    // sometimes image null or empty and that will cause app to crush
                    val campaignCoverImgRef = firebaseStorage.getReference("$CAMPAIGN_IMG_FOLDER/$coverImgName")
                    val orgImgRef = firebaseStorage.getReference("$GROUP_LOGO_IMG_FOLDER/$groupLogoImg")

                    GlideApp.with(mContext)
                            .load(campaignCoverImgRef)
                            .signature(ObjectKey(lastModificationDate.toTimestampString()))
                            .centerCrop()
                            .placeholder(R.drawable.campaign_placeholder_img)
                            .into(campaignCoverImgView)

                    GlideApp.with(mContext)
                            .load(orgImgRef)
                            .circleCrop()
                            .signature(ObjectKey(lastModificationDateForGroup.toTimestampString()))
                            .placeholder(R.drawable.group_logo_placeholder_img)
                            .into(orgImgView)

                } catch (e: Exception) {
                    Log.e(TAG, e.message)
                }

                orgImgView.setOnClickListener {
                    Log.d(TAG, "orgImgClicked")
                    mContext.startActivity<GroupActivity>(
                            //using campaign variable may be not good idea
                            //TODO:Thing more about this
                            CampaignActivity.EXTRA_KEY_GROUP_ID to groupId
                    )
                }

            }
        }


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return CampaignVH(LayoutInflater.from(parent.context).inflate(R.layout.campaign_view, parent, false))
    }


}