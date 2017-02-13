package com.hkllzh.gank.adapter.item.category_content

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.facebook.drawee.view.SimpleDraweeView
import com.hkllzh.gank.R
import me.drakeet.multitype.ItemViewProvider


/**
 * Created by lizheng on 2017/2/13.
 */
class CategoryWealViewProvider : ItemViewProvider<CategoryWeal, CategoryWealViewProvider.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, t: CategoryWeal) {
        val uri = Uri.parse(t.picUrl)
        holder.imavWeal.setImageURI(uri, holder.imavWeal.context)
    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_category_weal, parent, false)
        return ViewHolder(root)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imavWeal: SimpleDraweeView

        init {
            this.imavWeal = itemView.findViewById(R.id.imavWeal) as SimpleDraweeView
        }
    }
}