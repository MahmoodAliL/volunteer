package com.teaml.iq.volunteer.ui.main.home

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
import com.teaml.iq.volunteer.data.model.CampaignPost
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.ui.base.BaseViewHolder
import com.teaml.iq.volunteer.utils.AppConstants.CAMPAIGN_IMG_FOLDER
import com.teaml.iq.volunteer.utils.AppConstants.GROUP_LOGO_IMG_FOLDER
import org.jetbrains.anko.find

/**
 * Created by Mahmood Ali on 09/02/2018.
 */
class CampaignAdapter(private val mCampaignPostList: MutableList<CampaignPost>) : RecyclerView.Adapter<BaseViewHolder>() {


    companion object {
        val TAG: String = CampaignAdapter::class.java.simpleName

        private const val VIEW_TYPE_LOADING = 0
        private const val VIEW_TYPE_NORMAL = 1

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


    fun addCampaigns(campaignPostList: MutableList<CampaignPost>) {
        this.mCampaignPostList.addAll(campaignPostList)
        notifyDataSetChanged()
    }


    /**
     * Called when RecyclerView needs a new [ViewHolder] of the given type to represent
     * an item.
     *
     *
     * This new ViewHolder should be constructed with a new View that can represent the items
     * of the given type. You can either create a new View manually or inflate it from an XML
     * layout file.
     *
     *
     * The new ViewHolder will be used to display items of the adapter using
     * [.onBindViewHolder]. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary [View.findViewById] calls.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to
     * an adapter position.
     * @param viewType The itemView type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given itemView type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */


    inner class CampaignVH(itemView: View) : BaseViewHolder(itemView) {

        private val firebaseStorage = FirebaseStorage.getInstance()

        private val campaignTitle: TextView = itemView.find(R.id.campaignTitle)
        private val orgNameAndUploadDate = itemView.find<TextView>(R.id.orgNameAndUploadDate)
        private val orgImgView = itemView.find<ImageView>(R.id.orgImgView)
        private val campaignCoverImgView = itemView.find<ImageView>(R.id.campaignCoverImg)


        override fun clear() {
            campaignTitle.text = ""
            orgNameAndUploadDate.text = ""
            orgImgView.setImageDrawable(null)
            campaignCoverImgView.setImageDrawable(null)

        }

        override fun onBind(position: Int) {
            super.onBind(position)

            val item = mCampaignPostList[position]

            with(item) {

                campaignTitle.text = title
                // if we need to translate some string in text and make concatenating
                // them we should using this way

                // orgNameAndUploadDate.text = orgImgView.context.getString(R.string.group_name_and_date, groupName, uploadDate)
                val temp = "$groupName . $uploadDate"
                orgNameAndUploadDate.text = temp

                try {
                    // sometimes image null or empty and that will cause app to crush
                    val campaignCoverImgRef = firebaseStorage.getReference("$CAMPAIGN_IMG_FOLDER/$coverImgName")
                    val orgImgRef = firebaseStorage.getReference("$GROUP_LOGO_IMG_FOLDER/$groupLogoImg")

                    GlideApp.with(itemView.context)
                            .load(orgImgRef)
                            .circleCrop()
                            .placeholder(R.drawable.org_placeholder_img)
                            .into(orgImgView)

                    GlideApp.with(itemView.context)
                            .load(campaignCoverImgRef)
                            .centerCrop()
                            .placeholder(R.drawable.campaign_placeholder_img)
                            .into(campaignCoverImgView)

                } catch (e: Exception) {
                    Log.e(TAG, e.message)
                }



                orgImgView.setOnClickListener { Log.d(TAG, "orgImgClicked") }

                campaignCoverImgView.setOnClickListener { Log.d(TAG, "CampaignCoverImg Clicked") }
            }
        }

    }


    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return CampaignVH(LayoutInflater.from(parent?.context).inflate(R.layout.campaign_view, parent, false))
    }


    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int = mCampaignPostList.size

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the [ViewHolder.itemView] to reflect the item at the given
     * position.
     *
     *
     * Note that unlike [android.widget.ListView], RecyclerView will not call this method
     * again if the position of the item changes in the data set unless the item itself is
     * invalidated or the new position cannot be determined. For this reason, you should only
     * use the `position` parameter while acquiring the related data item inside
     * this method and should not keep a copy of it. If you need the position of an item later
     * on (e.g. in a click listener), use [ViewHolder.getAdapterPosition] which will
     * have the updated adapter position.
     *
     * Override [.onBindViewHolder] instead if Adapter can
     * handle efficient partial bind.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the
     * item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: BaseViewHolder?, position: Int) {
        holder?.onBind(position)
    }


}