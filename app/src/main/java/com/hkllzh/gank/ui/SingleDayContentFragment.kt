package com.hkllzh.gank.ui

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hkllzh.gank.R
import com.hkllzh.gank.adapter.item.category_content.CategoryContent
import com.hkllzh.gank.adapter.item.category_content.CategoryContentViewProvider
import com.hkllzh.gank.db.HistoryDataDB
import com.hkllzh.gank.event.GetAllDate
import com.hkllzh.gank.net.APIManager
import com.hkllzh.gank.net.GankApi
import com.hkllzh.gank.util.RxBus
import me.drakeet.multitype.Items
import me.drakeet.multitype.MultiTypeAdapter
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import kotlin.properties.Delegates


/**
 *
 */
class SingleDayContentFragment : Fragment() {

    private val TAG = "SingleDayContentFrg"

    private var recyclerView: RecyclerView by Delegates.notNull()

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


        adapter = MultiTypeAdapter()
        adapter?.register(CategoryContent::class.java, CategoryContentViewProvider())
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
                    Log.d(TAG, "it - >" + it.toString())

                    val l = 1..100
                    l.forEach {
                        item.add(CategoryContent("$it"))
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
