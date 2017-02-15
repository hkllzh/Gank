package com.hkllzh.gank.view

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import com.hkllzh.gank.R
import com.hkllzh.gank.adapter.item.category_content.CategoryContent
import com.hkllzh.gank.db.FavDB
import com.hkllzh.gank.util.BaseDialog

/**
 * Created by lizheng on 2017/2/14.
 */
class LongClickContentDialog(context: Context) : BaseDialog(context, R.layout.dia_long_click_content) {
    private val TAG = "LongClickContentDialog"
    private var date: CategoryContent? = null
    private var isFav: Boolean = false
    private var tvFav: TextView? = null

    override fun initView() {
        tvFav = findViewById(R.id.tvFav) as TextView
    }

    override fun initData() {
        tvFav?.setOnClickListener {
            if (isFav) {
                FavDB.newInstance()?.deleteFav(date!!)
            } else {
                FavDB.newInstance()?.add(date!!)
            }
            Toast.makeText(context, "操作成功", Toast.LENGTH_LONG).show()
            dismiss()
        }
    }

    fun setData(data: CategoryContent, isFav: Boolean): Unit {
        this.date = data
        this.isFav = isFav

        var text = ""
        if (isFav) {
            text = "取消收藏"
        } else {
            text = "收藏"
        }
        tvFav?.text = text
    }

}