package com.hkllzh.gank.adapter.item.category_content

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.hkllzh.gank.R
import me.drakeet.multitype.ItemViewProvider


/**
 * Created by lizheng on 2017/2/13.
 */
class CategoryTitleViewProvider : ItemViewProvider<CategoryTitle, CategoryTitleViewProvider.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, t: CategoryTitle) {
        holder.tvTitle.text = t.title
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_category_title, parent, false)
        return ViewHolder(root)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvTitle: TextView

        init {
            this.tvTitle = itemView.findViewById(R.id.tvTitle) as TextView
        }
    }
}