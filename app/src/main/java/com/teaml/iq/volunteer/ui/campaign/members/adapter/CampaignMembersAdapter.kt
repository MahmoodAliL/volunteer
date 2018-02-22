package com.teaml.iq.volunteer.ui.campaign.members.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
class CampaignMembersAdapter(val mMemberList: MutableList<CampaignMembers>) : RecyclerView.Adapter<BaseViewHolder>() {


    companion object {
        val TAG: String = CampaignMembersAdapter::class.java.simpleName
        private const val VISIBLE_THRESHOLD = 5
    }


    var isLoading = false
    var isFieldError = false

    var onLoading: (() -> Unit)? = null

    private var lastVisibleItem: Int = 0
    private var totalVisibleItem: Int = 0

    fun initRecyclerView(recyclerView: RecyclerView) {


        if (recyclerView.layoutManager is LinearLayoutManager) {
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    totalVisibleItem = layoutManager.itemCount
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                    if (!isFieldError && !isLoading && totalVisibleItem <= (lastVisibleItem + VISIBLE_THRESHOLD)) {
                        isLoading = true
                        onLoading?.invoke()
                    }
                }
            })
        }
    }

    fun setOnLoadingMoreListener(listener: () -> Unit) { onLoading = listener }

    fun setLoadMoreDone() {
        isLoading = false
    }



    fun addMembers(list: List<CampaignMembers>) {
        mMemberList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CampaignMembersVH {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.campaign_members_view, parent, false)
        return CampaignMembersVH(v)
    }

    override fun getItemCount() = mMemberList.size


    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        holder?.onBind(position)
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

            val model = mMemberList[position]

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

}