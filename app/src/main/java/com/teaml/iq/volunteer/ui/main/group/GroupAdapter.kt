package com.teaml.iq.volunteer.ui.main.group

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.teaml.iq.volunteer.R
import com.teaml.iq.volunteer.data.model.GlideApp
import com.teaml.iq.volunteer.data.model.GroupInfo
import com.teaml.iq.volunteer.ui.base.BaseViewHolder
import com.teaml.iq.volunteer.ui.main.home.CampaignAdapter
import com.teaml.iq.volunteer.utils.AppConstants
import de.hdodenhof.circleimageview.CircleImageView
import org.jetbrains.anko.find

/**
 * Created by Mahmood Ali on 11/02/2018.
 */
class GroupAdapter(private val listOfGroupInfo: MutableList<GroupInfo>) : RecyclerView.Adapter<BaseViewHolder>() {

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

    fun setOnLoadingMoreListener(listener: () -> Unit) { onLoading = listener }

    fun setLoadMoreDone() {
        isLoading = false
    }

    fun addGroups(listOfGroupInfo: MutableList<GroupInfo>) {
        this.listOfGroupInfo.addAll(listOfGroupInfo)
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
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     * @see .getItemViewType
     * @see .onBindViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return GroupVH(LayoutInflater.from(parent.context).inflate(R.layout.group_view, parent, false))
    }

    /**
     * Returns the total number of items in the data set held by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    override fun getItemCount(): Int  = listOfGroupInfo.size

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
    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }


    inner class GroupVH(itemView: View) : BaseViewHolder(itemView) {

        private val firebaseStorage = FirebaseStorage.getInstance()

        private val groupNameView = itemView.find<TextView>(R.id.groupName)
        private val memberNumberView = itemView.find<TextView>(R.id.memberNumber)
        private val campaignNumberView = itemView.find<TextView>(R.id.campaignNumber)
        private val groupImgView = itemView.find<CircleImageView>(R.id.groupImg)

        override fun clear() {
            groupImgView.setImageDrawable(null)
            groupNameView.text = ""
            memberNumberView.text = ""
            campaignNumberView.text = ""
        }


        override fun onBind(position: Int) {
            super.onBind(position)

            val group = listOfGroupInfo[position]

            with(group) {
                groupNameView.text = name
                campaignNumberView.text = itemView.context.getString(R.string.campaigns_number, campaignsNum)

                try {
                    val groupImgRef = firebaseStorage.getReference("${AppConstants.GROUP_LOGO_IMG_FOLDER}/$groupImg")

                    GlideApp.with(itemView.context)
                            .load(groupImgRef)
                            //.circleCrop()
                            .placeholder(R.drawable.org_placeholder_img)
                            .into(groupImgView)
                } catch (e: Exception) {
                    Log.e(TAG, e.message)
                }

            }
        }

    }
}