package com.hkllzh.gank.adapter.item.category_content

import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.hkllzh.gank.R
import me.drakeet.multitype.ItemViewProvider


/**
 * Created by lizheng on 2017/2/13.
 */
class CategoryContentViewProvider : ItemViewProvider<CategoryContent, CategoryContentViewProvider.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, t: CategoryContent) {
        holder.title.text = t.content.desc
        if (0 != t.content.images.size) {
            holder.imavPic.visibility = View.VISIBLE
            val uri = Uri.parse(t.content.images[0])
            holder.imavPic.setImageURI(uri,holder.imavPic.context)
        } else {
            holder.imavPic.visibility = View.GONE
        }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_category_content, parent, false)
        return ViewHolder(root)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView
        var imavPic: SimpleDraweeView

        init {
            this.title = itemView.findViewById(R.id.tvContent) as TextView
            this.imavPic = itemView.findViewById(R.id.imavPic) as SimpleDraweeView
        }
    }
}