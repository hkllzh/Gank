package com.hkllzh.gank.ui

import android.os.Bundle
import com.hkllzh.gank.adapter.item.category_content.CategoryContent
import com.hkllzh.gank.adapter.item.category_content.CategoryContent_FavViewProvider
import com.hkllzh.gank.db.FavDB
import com.hkllzh.gank.event.FavChangeEvent
import com.hkllzh.gank.util.RxBus
import java.util.*


/**
 * Created by lizheng on 2017/2/15.
 */
class FavFragment : BaseListFragment() {

    private val TAG = "FavFragment"

    private var data: ArrayList<CategoryContent> = ArrayList()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        data = FavDB.newInstance()?.findAll()!!

        if (0 == data.size) {
            noData("很抱歉！暂没有收藏数据")
        } else {
            onRefresh()
        }

        RxBus.getDefault().toObservable(FavChangeEvent::class.java).subscribe {
            onRefresh()
        }

    }

    override fun registerAdapterItem() {
        mAdapter?.register(CategoryContent::class.java, CategoryContent_FavViewProvider())
    }

    override fun onRefresh() {
        data = FavDB.newInstance()?.findAll()!!
        mItem.clear()
        data.forEach {
            mItem.add(it)
        }

        mAdapter?.setItems(mItem)
        mAdapter?.notifyDataSetChanged()
    }

    companion object {
        fun newInstance(): FavFragment {
            val fragment = FavFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}