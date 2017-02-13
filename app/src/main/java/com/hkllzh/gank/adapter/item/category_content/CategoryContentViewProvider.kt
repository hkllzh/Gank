package com.hkllzh.gank.adapter.item.category_content

import android.content.Intent
import android.net.Uri
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.view.SimpleDraweeView
import com.hkllzh.gank.R
import com.hkllzh.gank.util.PIC_OPTION_WIDTH_720
import me.drakeet.multitype.ItemViewProvider


/**
 * Created by lizheng on 2017/2/13.
 */
class CategoryContentViewProvider : ItemViewProvider<CategoryContent, CategoryContentViewProvider.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, t: CategoryContent) {
        holder.title.text = t.content.desc
        if (0 != t.content.images.size) {

            if (1 == t.content.images.size) {
                holder.imavPic.visibility = View.VISIBLE
                holder.llTwoPic.visibility = View.GONE

                holder.imavPic.controller = Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse(t.content.images[0] + PIC_OPTION_WIDTH_720))
                        .setAutoPlayAnimations(true)
                        .build()
            }

            if (t.content.images.size > 1) {
                holder.imavPic.visibility = View.GONE
                holder.llTwoPic.visibility = View.VISIBLE

                holder.imavPicFirst.controller = Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse(t.content.images[0] + PIC_OPTION_WIDTH_720))
                        .setAutoPlayAnimations(true)
                        .build()

                holder.imavPicSecond.controller = Fresco.newDraweeControllerBuilder()
                        .setUri(Uri.parse(t.content.images[1] + PIC_OPTION_WIDTH_720))
                        .setAutoPlayAnimations(true)
                        .build()
            }


        } else {

            holder.imavPic.visibility = View.GONE
            holder.llTwoPic.visibility = View.GONE
        }

        holder.rootView.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW);    //为Intent设置Action属性
            intent.data = Uri.parse(t.content.url) //为Intent设置DATA属性
            startActivity(holder.rootView.context, intent, null);
        }

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): ViewHolder {
        val root = inflater.inflate(R.layout.item_category_content, parent, false)
        return ViewHolder(root)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val title: TextView
        val imavPic: SimpleDraweeView
        val imavPicFirst: SimpleDraweeView
        val imavPicSecond: SimpleDraweeView
        val rootView: View
        val llTwoPic: View

        init {
            this.title = itemView.findViewById(R.id.tvContent) as TextView
            this.imavPic = itemView.findViewById(R.id.imavPic) as SimpleDraweeView
            this.rootView = itemView
            this.llTwoPic = itemView.findViewById(R.id.llTwoPic)
            this.imavPicFirst = itemView.findViewById(R.id.imavPicFirst) as SimpleDraweeView
            this.imavPicSecond = itemView.findViewById(R.id.imavPicSecond) as SimpleDraweeView
        }
    }
}