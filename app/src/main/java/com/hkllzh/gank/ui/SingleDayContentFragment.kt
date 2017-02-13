package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.hkllzh.gank.R
import com.hkllzh.gank.adapter.item.category_content.*
import com.hkllzh.gank.bean.SingleContent
import com.hkllzh.gank.db.HistoryDataDB
import com.hkllzh.gank.event.GetAllDate
import com.hkllzh.gank.net.APIManager
import com.hkllzh.gank.net.GankApi
import com.hkllzh.gank.util.DEFAULT_CATEGORY_ORDER
import com.hkllzh.gank.util.RxBus
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


/**
 *
 */
class SingleDayContentFragment : Fragment() {

    private val TAG = "SingleDayContentFrg"

    private var recyclerView: RecyclerView by Delegates.notNull()
    private var swipeRefreshLayout: SwipeRefreshLayout by Delegates.notNull()

    private var dateStr = ""
    private val item = Items()
    private var adapter: MultiTypeAdapter? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater!!.inflate(R.layout.fragment_content, container, false)
        recyclerView = v.findViewById(R.id.recyclerView) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)

        swipeRefreshLayout = v.findViewById(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        swipeRefreshLayout.setOnRefreshListener {
            Observable.timer(300, TimeUnit.MILLISECONDS).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        swipeRefreshLayout.isRefreshing = false
                    }
        }


        adapter = MultiTypeAdapter()
        adapter?.register(CategoryContent::class.java, CategoryContentViewProvider())
        adapter?.register(CategoryTitle::class.java, CategoryTitleViewProvider())
        adapter?.register(CategoryWeal::class.java, CategoryWealViewProvider())
        recyclerView.adapter = adapter

        return v
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(TAG, "onActivityCreated")

        dateStr = HistoryDataDB.newInstance()?.latest()!!
        Log.d(TAG, "lastDate = $dateStr" + this + Thread.currentThread().id)
        if (!TextUtils.isEmpty(dateStr)) {
            requestDayContent(dateStr)
        }

        RxBus.getDefault().toObservable(GetAllDate::class.java).subscribe {
            val date = HistoryDataDB.newInstance()?.latest()!!
            Log.d(TAG, "dateStr = $date" + this + Thread.currentThread().id)
            if (date == dateStr) {
                Log.d(TAG, "不需要更新")
            } else {
                Log.d(TAG, "需要更新")
                requestDayContent(date)
            }
        }

//        APIManager.getApi(GankApi::class.java).category("Android", 2, 1)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        { jsonObject ->
//                            Log.d(TAG, "onNext-->" + jsonObject.toString())
//                        },
//                        {
//                            Log.d(TAG, "onError-->" + it)
//                            it?.printStackTrace()
//                        },
//                        {
//                            Log.d(TAG, "onCompleted-->")
//                        }
//                )

//        APIManager.getApi(GankApi::class.java).haveDataHistory()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    Log.d(TAG, it.toString())
//                    tvTest.text = it.toString()
//                }
    }

    private fun requestDayContent(d: String) {
        APIManager.getApi(GankApi::class.java).day(d.split("-")[0].toInt(), d.split("-")[1].toInt(), d.split("-")[2].toInt())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    jsonObject ->

                    var category: ArrayList<String>? = null
                    if (jsonObject.has("category") && jsonObject.get("category").isJsonArray) {
                        category = ArrayList()
                        jsonObject.get("category").asJsonArray.forEach {
                            category?.add(it.asString)
                        }
                    }

                    if (null == category || 0 == category.size) {
                        return@subscribe
                    }

                    Collections.sort(category, {
                        s1, s2 ->
                        var i1: Int = 10
                        var i2: Int = 10

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

                    item.clear()

                    if (jsonObject.has("results") && jsonObject.get("results").isJsonObject) {
                        category.forEach {
                            title ->
                            val jsonResult = jsonObject.get("results").asJsonObject
                            if (DEFAULT_CATEGORY_ORDER[0] != title) {
                                item.add(CategoryTitle(title))
                            }
                            jsonResult.get(title).asJsonArray.forEach {
                                val single: SingleContent = Gson().fromJson(it, SingleContent::class.java)
                                if (DEFAULT_CATEGORY_ORDER[0] == title) {
                                    // 福利
                                    item.add(CategoryWeal(it.asJsonObject["url"].asString))
                                } else {
                                    item.add(CategoryContent(single))
                                }

                            }
                        }
                    }

                    adapter?.setItems(item)
                    adapter?.notifyDataSetChanged()


                }, {}, {})
    }

    companion object {
        // TODO: Rename and change types and number of parameters
        fun newInstance(): SingleDayContentFragment {
            val fragment = SingleDayContentFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
