package com.hkllzh.gank.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.hkllzh.gank.adapter.item.category_content.*
import com.hkllzh.gank.bean.SingleContent
import com.hkllzh.gank.db.ContentDB
import com.hkllzh.gank.db.HistoryDataDB
import com.hkllzh.gank.event.GetAllDate
import com.hkllzh.gank.net.APIManager
import com.hkllzh.gank.net.GankApi
import com.hkllzh.gank.util.DEFAULT_CATEGORY_ORDER
import com.hkllzh.gank.util.RxBus
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*

/**
 *
 */
class SingleDayContentFragment : BaseListFragment() {

    private val TAG = "SingleDayContentFrg"

    private var dateStr = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")

        dateStr = HistoryDataDB.newInstance()?.latest()!!
        Log.d(TAG, "lastDate = $dateStr" + this + Thread.currentThread().id)
        if (!TextUtils.isEmpty(dateStr)) {
            val content = ContentDB.newInstance()?.findContentByDate(dateStr)
            if (!TextUtils.isEmpty(content)) {
                val jsonObject = Gson().fromJson(content, JsonObject::class.java)
                showData(jsonObject)
            } else {
                requestDayContent(dateStr)
            }

        }

        RxBus.getDefault().toObservable(GetAllDate::class.java).subscribe {
            val date = HistoryDataDB.newInstance()?.latest()!!
            if (date == dateStr) {
                Log.d(TAG, "不需要更新")
            } else {
                Log.d(TAG, "需要更新")
                this.dateStr = date
                requestDayContent(date)
            }
        }
    }

    override fun registerAdapterItem() {
        mAdapter?.register(CategoryContent::class.java, CategoryContentViewProvider())
        mAdapter?.register(CategoryTitle::class.java, CategoryTitleViewProvider())
        mAdapter?.register(CategoryWeal::class.java, CategoryWealViewProvider())
    }

    override fun onRefresh() {
        requestDayContent(d = dateStr)
    }

    private fun requestDayContent(d: String) {
        APIManager.getApi(GankApi::class.java).day(d.split("-")[0].toInt(), d.split("-")[1].toInt(), d.split("-")[2].toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    jsonObject ->

                    ContentDB.newInstance()?.add(d, jsonObject.toString())

                    if (showData(jsonObject)) return@subscribe


                }, {}, {})
    }

    /**
     * 显示数据
     */
    private fun showData(jsonObject: JsonObject): Boolean {
        var category: ArrayList<String>? = null
        if (jsonObject.has("category") && jsonObject.get("category").isJsonArray) {
            category = ArrayList()
            jsonObject.get("category").asJsonArray.forEach {
                category?.add(it.asString)
            }
        }

        if (null == category || 0 == category.size) {
            return true
        }

        Collections.sort(category, {
            s1, s2 ->
            var i1: Int = DEFAULT_CATEGORY_ORDER.size * 2
            var i2: Int = DEFAULT_CATEGORY_ORDER.size * 2

            DEFAULT_CATEGORY_ORDER.forEachIndexed { index, value ->
                if (value == s1) {
                    i1 = index
                    return@forEachIndexed
                }
            }

            DEFAULT_CATEGORY_ORDER.forEachIndexed { index, value ->
                if (value == s2) {
                    i2 = index
                    return@forEachIndexed
                }
            }

            return@sort i1 - i2
        })

        mItem.clear()

        if (jsonObject.has("results") && jsonObject.get("results").isJsonObject) {
            category.forEach {
                title ->
                val jsonResult = jsonObject.get("results").asJsonObject
                if (DEFAULT_CATEGORY_ORDER[0] != title) {
                    mItem.add(CategoryTitle(title))
                }
                jsonResult.get(title).asJsonArray.forEach {
                    val single: SingleContent = Gson().fromJson(it, SingleContent::class.java)
                    if (DEFAULT_CATEGORY_ORDER[0] == title) {
                        // 福利
                        mItem.add(CategoryWeal(it.asJsonObject["url"].asString))
                    } else {
                        mItem.add(CategoryContent(single))
                    }

                }
            }
        }

        mAdapter?.setItems(mItem)
        mAdapter?.notifyDataSetChanged()
        return false
    }

    companion object {
        fun newInstance(): SingleDayContentFragment {
            val fragment = SingleDayContentFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
