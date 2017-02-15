package com.hkllzh.gank.view

import android.content.Context
import android.widget.TextView
import android.widget.Toast
import com.hkllzh.gank.R
import com.hkllzh.gank.adapter.item.category_content.CategoryContent
import com.hkllzh.gank.db.FavDB
import com.hkllzh.gank.event.FavChangeEvent
import com.hkllzh.gank.util.BaseDialog
import com.hkllzh.gank.util.RxBus

/**
 * Created by lizheng on 2017/2/14.
 */
class LongClickContentDialog(context: Context, date: CategoryContent) : BaseDialog(context, R.layout.dia_long_click_content) {
    private var date: CategoryContent? = null
    private var tvFav: TextView? = null

    init {
        this.date = date
    }

    override fun initView() {
        tvFav = `$`(R.id.tvFav)

        tvFav?.setOnClickListener {
            Toast.makeText(context, "收藏", Toast.LENGTH_LONG).show()
            FavDB.newInstance()?.add(date!!)
            RxBus.getDefault().post(FavChangeEvent())
            dismiss()
        }
    }

    override fun initData() {

    }

}