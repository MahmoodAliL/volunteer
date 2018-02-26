package com.teaml.iq.volunteer.ui.group.detail

import android.support.v7.widget.LinearLayoutManager
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
import com.teaml.iq.volunteer.utils.toDateString
import org.jetbrains.anko.find

/**
 * Created by Mahmood Ali on 18/02/2018.
 */
class GroupCampaignsAdapter(private val mList: MutableList<GroupCampaigns>) : RecyclerView.Adapter<BaseViewHolder>() {

    companion object {
        val TAG: String = GroupCampaignsAdapter::class.java.simpleName

        private const val VISIBLE_THRESHOLD = 2
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

    fun setOnLoadingMoreListener(listener: () -> Unit) {
        onLoading = listener
    }

    fun setLoadMoreDone() {
        isLoading = false
    }


    var onItemClick:((String)->Unit)? = null

    inner class GroupCampaignsVH(view: View) : BaseViewHolder(view) {

        private val imgView = view.find<ImageView>(R.id.campaignCoverImgView)
        private val txtTitle = view.find<TextView>(R.id.txtCampaignTitle)
        private val txtUploadDate = view.find<TextView>(R.id.txtUploadDate)

        init {
            view.setOnClickListener {
                Log.i("click","item is clicked on the position $currentPosition")
                // send the id to groupDetailFragment
                onItemClick?.invoke(mList[currentPosition].campaignId)
            }
        }

        override fun clear() {
            imgView.setImageDrawable(null)
            txtTitle.text = ""
            txtUploadDate.text = ""
        }

        override fun onBind(position: Int) {
            super.onBind(position)

            val item = mList[position]
            txtTitle.text = item.title
            txtUploadDate.text = item.uploadDate.toDateString()

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


    fun setOnViewItemClick(ref:(String)->Unit) {
        onItemClick = ref
    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {

        val view = LayoutInflater.from(parent?.context).inflate(R.layout.group_campaigns_view, parent, false)
        return GroupCampaignsVH(view)
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        holder?.onBind(position)
    }

    fun addCampaign(campaignInfoList: MutableList<GroupCampaigns>) {
        this.mList.addAll(campaignInfoList)
        Log.e(TAG,"the number is ${mList.size}")
        notifyDataSetChanged()
    }


}