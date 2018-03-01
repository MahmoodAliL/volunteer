package com.teaml.iq.volunteer.ui.base

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by Mahmood Ali on 27/02/2018.
 */
abstract class BaseRecyclerAdapter<T>(protected val mList: MutableList<T>) : RecyclerView.Adapter<BaseViewHolder>() {


    companion object {
        val TAG: String = BaseRecyclerAdapter::class.java.simpleName
    }

    //mVisibleThreshold
    private var mVisibleThreshold = 2

    protected var isLoading = false

    private var onLoading: (() -> Unit)? = null

    private var isLoadMoreEnable = true

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

                    if (isLoadMoreEnable && mList.isNotEmpty() && !isLoading && totalVisibleItem <= (lastVisibleItem + mVisibleThreshold)) {
                        isLoading = true
                        onLoading?.invoke()
                    }
                }
            })
        }
    }

    fun setVisibleThreshold(visibleThreshold: Int) {
        this.mVisibleThreshold = visibleThreshold
    }

    fun setOnLoadMoreListener(listener: () -> Unit) {
        onLoading = listener
    }

    fun setLoadMoreComplete() {
        isLoading = false
    }

    fun enableLoadMore(isEnable: Boolean) {
        isLoadMoreEnable = isEnable
    }

    /*fun enableLoadMore() {
        isLoadMoreEnable = true
    }

    fun disableLoadMore() {
        isLoadMoreEnable = false
    }*/

    fun addItems(list: List<T>) {
        mList.addAll(list)
        notifyDataSetChanged()
    }

    fun clearList() {
        mList.clear()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.onBind(position)
    }


}